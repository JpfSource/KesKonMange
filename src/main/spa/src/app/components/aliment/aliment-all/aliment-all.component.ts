import { Component, OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import { AlimentService } from 'src/app/shared/services/aliment.service';
import { Aliment } from 'src/app/shared/models/aliment';
import { Person } from 'src/app/shared/models/person';
import { PersonService } from 'src/app/shared/services/person.service';
import { Score } from 'src/app/shared/models/score';

@Component({
    selector: 'app-aliment-all',
    templateUrl: './aliment-all.component.html',
    styleUrls: ['./aliment-all.component.scss']
  })
export class AlimentAllComponent implements OnInit {

    aliments$ = new BehaviorSubject<Aliment [] | any>([]);
    person!: Person | null;

    constructor(
      private _alimentService : AlimentService,
      private _route: ActivatedRoute,
      private _personService: PersonService,
      private _router : Router
    ) { }
  
    ngOnInit(): void {
        this._route.paramMap.subscribe(param => {
            const personId = Number(param.get('id'));
            if(personId != null && personId > 0) {
                this._personService.getPersonById(personId);
                this._personService.person$.subscribe((p: Person | null) => {
                    this.person = p;
                });
            }
        });
    }

    getAlimentsByTag(tag : String) : void {
            this._alimentService.getAlimentByTag(tag).subscribe(aliment => {
                this.aliments$.next(aliment)
            })
              
    }

    getNutriscore(): Score {
        this.aliments$.forEach((a: Aliment | null) =>{
            a!.alimentScores.forEach((s: Score | any) =>{
                if(s?.typeScore == "NUTRI_SCORE"){
                    return s;
                }                       
            });
        })
        
        return new Score;
    }
    
    getEcoscore(): Score {
        this.aliments$.forEach((a: Aliment | null) =>{
            a!.alimentScores.forEach((s: Score | any)=>{
                if(s?.typeScore == "ECO_SCORE"){
                    return s;
                }                       
            });
        });
        
        return new Score;
    }
    getNovagroup(): Score {
        this.aliments$.forEach((a: Aliment | null) =>{
            a!.alimentScores.forEach((s: Score | any)=>{
                if(s?.typeScore == "NOVA_GROUPE"){
                    return s;
                }              
            });
        });
        
        return new Score;
    }
    
    goBack(): void {
        this._router.navigateByUrl("/home");
    }
}