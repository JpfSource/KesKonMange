import { Injectable, OnDestroy } from '@angular/core';
import { environment } from 'src/environments/environment';
import { BehaviorSubject, Observable } from 'rxjs';
import {Score} from '../models/score';
import { TokenStorageService } from './token-storage.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class ScoreService implements OnDestroy {
    
    private _urlScore = environment.urlApi + '/api/scores';

    public score$ = new BehaviorSubject<Score>(new Score());
  
    // A FAIRE DANS TOUTES LES REQUETES VERS LE BACK
   
    textHeader = 'Bearer '+ this.tokenStorage.getToken()!;
    
    httpOptions = {
        headers: new HttpHeaders({ 'Authorization':  this.textHeader})
    };
    
    constructor(private _http: HttpClient,private tokenStorage: TokenStorageService){    
    }
    
    ngOnDestroy(): void {
        this.score$.unsubscribe();
    }

    /**
    * Méthode qui retourne toutes les scores présents en BdD.
    * @returns
    */
    public getScoreAll() {
        return this._http.get<Score[]>(this._urlScore+"/all", this.httpOptions)
    }

    /**
    * Méthode qui permet d'avoir les données d'une score dont l'id est passé en paramètre.
    * @param scoreId
    */
    public getScoreById(scoreId: number) {
        const url = this._urlScore + '/' + scoreId;

        this._http.get<Score>(url).subscribe(score => {
            this.score$.next(score);
        });
    }

   
}