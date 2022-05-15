import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnDestroy } from '@angular/core';
import { BehaviorSubject, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Person } from '../models/person';
import { TokenStorageService } from './token-storage.service';


@Injectable({
  providedIn: 'root'
})
export class PersonService implements OnDestroy {

  private _urlPerson = environment.urlApi + '/api/personnes';

  public person$ = new BehaviorSubject<Person>(new Person());
  public persons$ = new BehaviorSubject<Person[]>([]);

  // A FAIRE DANS TOUTES LES REQUETES VERS LE BACK
  textHeader = 'Bearer '+ this.tokenStorage.getToken()!;

  httpOptions = {
    headers: new HttpHeaders({ 'Authorization':  this.textHeader})
  };

  constructor(
    private _http: HttpClient,
    private tokenStorage: TokenStorageService
  ) { }

  ngOnDestroy(): void {
    this.person$.unsubscribe();
  }

  /**
   * Méthode qui retourne toutes les personnes présentes en BdD.
   * @returns
   */
  public getPersonAll() {
   return this._http.get<Person[]>(this._urlPerson+"/all", this.httpOptions)
  }

  /**
   * Méthode qui permet d'avoir les données d'une personne dont l'id est passé en paramètre.
   * @param personId
   */
  public getPersonById(personId: number) {
    const url = this._urlPerson + '/' + personId;

    return this._http.get<Person>(url)
      .pipe(
        tap(person => {
          this.person$.next(person);
        })
      )
  }

  /**
   * Méthode qui permet de mettre à jour les données du profil d'une personne dont l'id est passé en paramètre.
   * @param person
   * @param personId
   */
  update(person: Person) {
    return this._http.put<Person>(this._urlPerson + '/' + person.id, person, this.httpOptions)
      .pipe(
        tap(person => {
          this.person$.next(person);
        }
        )
      )

  }

  changeData(person: Person) {
    return this._http
      .put<number>(this._urlPerson + '/recalcul', person, this.httpOptions)
      .pipe(
        tap(newBC => {
          person.besoinsCaloriques = newBC;
          this.person$.next(person);
        })
      );
  }

  /**
   * Méthode qui permet de créer une personne par un utilisateur
   * @param person
   * @param idCreateur
   * @returns
   */
  createPersonByUser(person: Person, idCreateur: number | undefined) {
    return this._http.post<Person>(this._urlPerson + '/' + idCreateur + '/create', person, this.httpOptions)
    .pipe(
      tap(person => {
        this.person$.next(person);
      }
      )
    )
  }

/**
 * Méthode qui retourne la liste de toutes les personnes créées par un utilisateur.
 * @param idCreateur
 * @returns
 */
  getAllPersonsCreatedByUser(idCreateur: number) {
    return this._http.get<Person[]>(this._urlPerson +'/' + idCreateur + '/all-personnes', this.httpOptions )
  }

}
