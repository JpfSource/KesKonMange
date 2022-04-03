import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { CriteriaComponent } from 'src/app/shared/criteria/criteria.component';
import { Person } from 'src/app/shared/models/person';
import { Plat } from 'src/app/shared/models/plat';
import { PersonService } from 'src/app/shared/services/person.service';
import { PlatService } from 'src/app/shared/services/plat.service';

@Component({
  selector: 'app-person-plats',
  templateUrl: './person-plats.component.html',
  styleUrls: ['./person-plats.component.scss']
})
export class PersonPlatsComponent implements OnInit, AfterViewInit {


  person!: Person | null;
  plats$ = new BehaviorSubject<Plat[]>([]);
  message!: string;
  typePlatInputSaved!: string;
  mesPlatsFiltres$ = new BehaviorSubject<Plat[]>([]);

  @ViewChild(CriteriaComponent)
  filterComponent!: CriteriaComponent;
  parentListFilter!: string;

  typePlats: string[] = ["Entrée", "Plat principal", "Dessert", "Laitage", "Boisson", "Céréales"]

  constructor(
    private _personService: PersonService,
    private _platService: PlatService,
    private _route: ActivatedRoute,
    private _router: Router
  ) { }

  /**
   * Méthode qui permet de récupérer dans le composant parent la valeur dans le composant fille (CriteriaComponent)
   */
  ngAfterViewInit(): void {
    this.parentListFilter = this.filterComponent.listFilter;
  }

  private typeSelectedSubject = new BehaviorSubject<string>("");
  typeSelectedAction$ = this.typeSelectedSubject.asObservable();

  /**
   * Méthode qui permet de filtrer la liste en fonction du type de plat
   * (select & option de la vue).
   * @param typePlatInput
   */
  onSelected(typePlatInput: string) {
    this.typePlatInputSaved = typePlatInput;
    this.mesPlatsFiltres$.next(this.plats$.value);
    if(typePlatInput != "") {
      this.mesPlatsFiltres$.next(
        this.mesPlatsFiltres$.value
        .filter(plat =>
          plat.typePlatLibelle == typePlatInput!
          ))
    }
  }

  /**
   * Méthode qui permet de récupérer tous les plats en BdD, en mettant à jour les observables
   * plats$ et mesPlatsFiltres$.
   */
  getAllPlats(): void {
    this._platService.findAll().subscribe(plats => {
      this.plats$.next(plats);
      this.mesPlatsFiltres$.next(plats);
    });
  }

/**
 * Méthode qui écoute la saisie dans le champs de recherche et transmet
 * la value à la méthode de filtre.
 * @param value
 */
  onValueChange(value: string): void {
    this.performFilter(value);
  }

  /**
   * Méthode qui permet de filter les plats en fonction du libellé (saisi dans le champs de recheche).
   * @param filter
   */
  performFilter(filter?: string): void {
    if(filter){
      this.mesPlatsFiltres$.next(
        this.mesPlatsFiltres$.value
        .filter(plat =>
          plat.libelle?.toLocaleLowerCase()
          .includes(filter!.toLocaleLowerCase()))
          )
    }
    else {
      this.mesPlatsFiltres$.next(this.plats$.value);
      this.onSelected(this.typePlatInputSaved);
    }
  }

  ngOnInit(): void {
    this._route.paramMap.subscribe(param => {
      const personId = Number(param.get('id'));
      if (personId != null && personId > 0) {
        this._personService.getPersonById(personId);
        this._personService.person$.subscribe((p: Person | null) => {
          this.person = p;
        });
      }
    });
    this.getAllPlats();
  }

  /**
   * Méthode qui permet de supprimer un plat sélectionné dans la liste,
   * elle affiche un message et met à jour la liste une fois le plat supprimé
   * @param id
   */
  public deletePlat(id: any): void {
    this._platService.deletePlat(id).subscribe(() => {
      this.message = "Plat supprimé avec succès !"
      this.getAllPlats();
      setTimeout(() => this.message = "", 2500);
    });

  }

}
