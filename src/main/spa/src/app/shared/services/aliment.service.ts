import { Injectable, OnDestroy } from '@angular/core';
import { environment } from 'src/environments/environment';
import { BehaviorSubject, Observable } from 'rxjs';
import {Aliment} from '../models/aliment';
import { TokenStorageService } from './token-storage.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Score } from '../models/score';

@Injectable({
    providedIn: 'root'
})
export class AlimentService implements OnDestroy {
    
    private _urlAliment = environment.urlApi + '/api/aliments';

    public aliment$ = new BehaviorSubject<Aliment>(new Aliment());
  
    // A FAIRE DANS TOUTES LES REQUETES VERS LE BACK
   
    textHeader = 'Bearer '+ this.tokenStorage.getToken()!;
    
    httpOptions = {
        headers: new HttpHeaders({ 'Authorization':  this.textHeader})
    };
    
    constructor(private _http: HttpClient,private tokenStorage: TokenStorageService){    
    }
    
    ngOnDestroy(): void {
        this.aliment$.unsubscribe();
    }

    /**
    * Méthode qui retourne toutes les aliments présents en BdD.
    * @returns
    */
    public getAlimentAll() {
        return this._http.get<Aliment[]>(this._urlAliment+"/all", this.httpOptions)
    }

    /**
    * Méthode qui permet d'avoir les données d'une aliment dont l'id est passé en paramètre.
    * @param alimentId
    */
    public getAlimentById(alimentId: number) {
        const url = this._urlAliment + '/' + alimentId;

        this._http.get<Aliment>(url).subscribe(aliment => {
            this.aliment$.next(aliment);
        });
    }

    /**
    * Méthode qui permet d'avoir les données d'une aliment dont l'id est passé en paramètre.
    * @param alimentTag
    */
     public getAlimentByTag(alimentTag: String) {
        return this._http.get<Aliment[]>(this._urlAliment+"/"+alimentTag, this.httpOptions);
    }

    public getScoreOfAliment(aId: number){
        return this._http.get<Score[]>(this._urlAliment+"/Score/"+aId, this.httpOptions);
    }
}
  