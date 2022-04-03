import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { Plat } from 'src/app/shared/models/plat';
import { PlatService } from 'src/app/shared/services/plat.service';

@Component({
  selector: 'app-item-plat',
  templateUrl: './item-plat.component.html',
  styleUrls: ['./item-plat.component.scss']
})
export class ItemPlatComponent implements OnInit {

  plat!: Plat;
  plats$ = new BehaviorSubject<Plat [] | any>([]);
  status!: string;
  platId!: number;

  constructor(
    private _platService: PlatService,
    private _route : ActivatedRoute,
    private _http: HttpClient,
    private location: Location
  ) { }

  ngOnInit(): void {
    this._route.paramMap.subscribe(param => {
      this.platId = Number(param.get('id'));
      if(this.platId != null && this.platId > 0) {
        this._platService.getPlatById(this.platId);
        this._platService.plat$.subscribe((pl: Plat) => {
          this.plat = pl;
        });
      }
    });

  }

  public deletePlat(): void {
    this._platService.deletePlat(this.platId)
    }

    goBack(): void {
      this.location.back();
    }
}



