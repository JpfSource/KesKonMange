import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable, ReplaySubject, tap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Person } from '../models/person';
import { TokenStorageService } from './token-storage.service';

@Injectable({
  providedIn: 'root'
})
/**
 * Service qui envoie les requêtes HTTP POST le signin et login au server
 */
export class AuthService {

  private _urlAuth = environment.urlApi + '/api/utilisateurs';

  public user$ = new BehaviorSubject<Person | null>(null);
  public isLoggedIn$ = new ReplaySubject<boolean>(1);

  constructor(
    private _http: HttpClient,
    private tokenStorage: TokenStorageService
  ) { }

  handleError(error: any) {
    return throwError(() => error)
  }

  /**
   * Méthode POST de l'Utilisateur lors de son identification.
   * @param person
   * @returns
   */
  login(person: Person): Observable<any> {
    return this._http.post<Person>(this._urlAuth + '/login', person)
      .pipe(
        tap(person => {
          this.user$.next(person);
          this.isLoggedIn$.next(!!person);
        }),
        catchError(this.handleError)
      );

  }

  /**
   * Permet d'ajouter dans le JWT dans le header.
   */
  textHeader = 'Bearer ' + this.tokenStorage.getToken()!;
  httpOptions = {
    headers: new HttpHeaders({ 'Authorization': this.textHeader })
  };

  /**
   * Méthode qui vérifie en BdD si le JWt est valide.
   * @returns
   */
  checkIfUserIsConnected() {
    return this._http.get(this._urlAuth + "/connected", this.httpOptions)
      .pipe(
        tap(resp => {
          this.isLoggedIn$.next(!!resp)
        })
      )
  }

  /**
   * Méthode POST de l'Utilisateur lors de l'enregistrement.
   * @param person
   * @returns
   */
  signin(person: Person): Observable<any> {
    return this._http.post<Person>(this._urlAuth + '/signin', person);
  }

  logout() {
    this.tokenStorage.signOut();
    this.isLoggedIn$.next(false);
  }

}







