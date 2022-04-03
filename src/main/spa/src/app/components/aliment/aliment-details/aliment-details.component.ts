import { Component, OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { AlimentService } from 'src/app/shared/services/aliment.service';
import { Aliment } from 'src/app/shared/models/aliment';
import { Person } from 'src/app/shared/models/person';
import { PersonService } from 'src/app/shared/services/person.service';
import { Score } from 'src/app/shared/models/score';

@Component({
    selector: 'app-aliment-details',
    templateUrl: './aliment-details.component.html',
    styleUrls: ['./aliment-details.component.scss']
  })
export class AlimentDetailsComponent implements OnInit {

    aliment!: Aliment;
    person!: Person | null;

    constructor(
      private _alimentService : AlimentService,
      private _route: ActivatedRoute,
      private _personService: PersonService,
      private _router : Router
    ) { }
  
    ngOnInit(): void {
        this._route.paramMap.subscribe(param => {
            const personId = Number(param.get('pId'));
            if(personId != null && personId > 0) {
                this._personService.getPersonById(personId);
                this._personService.person$.subscribe((p: Person | null) => {
                    this.person = p;
                });
            }

            const alimentId = Number(param.get('aId'));
            if(alimentId != null && alimentId > 0) {
                this._alimentService.getAlimentById(alimentId);
                this._alimentService.aliment$.subscribe((a: Aliment) => {
                    this.aliment = a;
                    console.log(this.aliment);
                });
            }
        });
    }

    getNutriscore(): Score {
        console.log(this.aliment!.alimentScores);
        this.aliment!.alimentScores.forEach((s: Score | any) =>{
                if(s?.typeScore == "NUTRI_SCORE"){
                    return s;
                }
        });
        return new Score;
    }
    getEcoscore(): Score {
        this.aliment!.alimentScores.forEach((s: Score | any)=>{
                if(s?.typeScore == "ECO_SCORE"){
                    return s;
                }                       
        });
        return new Score;
    }
    getNovagroup(): Score {
        this.aliment!.alimentScores.forEach((s: Score | any)=>{
                if(s?.typeScore == "NOVA_GROUPE"){
                    return s;
                }              
        });
        return new Score;
    }
    
    goBack(): void {
        this._router.navigateByUrl("/aliments");
    }
}