import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Plat } from '../models/plat';

@Injectable({
  providedIn: 'root'
})
export class PlatService {

  private _urlPlat = environment.urlApi + '/api/plats';

  public plat$ = new BehaviorSubject<Plat>(new Plat());
  selectPlat: any;
  url!: string;
  public plats$ = new BehaviorSubject<Plat [] | any>([]);

  public onEditItemEntree() {
    return this._http.get(environment.urlApi)
  }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private _http: HttpClient
  ) {
    this.findAll()
   }

  public getPlatById(platId: Number) {
    const url = this._urlPlat + '/' + platId;
    this._http.get<Plat>(url)
      .subscribe(plat => {
        this.plat$.next(plat);
      })
  }

  public updateProfil(plat: Plat) {
    const url = this._urlPlat + '/plat/' + plat.id;
    this._http.patch<Plat>(url, plat, this.httpOptions)
      .subscribe(value => {
        this.plat$.next(value);
      })
  }

  public findAll() {
    return this._http.get<Plat[]>(environment.urlApi + '/api/plats')
  }

  public deletePlat(id: number) {
    const url = this._urlPlat + '/' + id;
    return this._http.delete(url)
  }
}
