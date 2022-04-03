import { Injectable, OnDestroy } from '@angular/core';
import { environment } from 'src/environments/environment';
import { BehaviorSubject, Observable } from 'rxjs';
import {Allergie} from '../models/allergie';
import { TokenStorageService } from './token-storage.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class AllergieService implements OnDestroy {
    
    private _urlAllergie = environment.urlApi + '/api/allergies';

    public allergie$ = new BehaviorSubject<Allergie>(new Allergie());
  
    // A FAIRE DANS TOUTES LES REQUETES VERS LE BACK
   
    textHeader = 'Bearer '+ this.tokenStorage.getToken()!;
    
    httpOptions = {
        headers: new HttpHeaders({ 'Authorization':  this.textHeader})
    };
    
    constructor(private _http: HttpClient,private tokenStorage: TokenStorageService){    
    }
    
    ngOnDestroy(): void {
        this.allergie$.unsubscribe();
    }

    /**
    * Méthode qui retourne toutes les allergies présents en BdD.
    * @returns
    */
    public getAllergieAll() {
        return this._http.get<Allergie[]>(this._urlAllergie+"/all", this.httpOptions)
    }

    /**
    * Méthode qui permet d'avoir les données d'une allergie dont l'id est passé en paramètre.
    * @param allergieId
    */
    public getAllergieById(allergieId: number) {
        const url = this._urlAllergie + '/' + allergieId;

        this._http.get<Allergie>(url).subscribe(allergie => {
            this.allergie$.next(allergie);
        });
    }

   
}