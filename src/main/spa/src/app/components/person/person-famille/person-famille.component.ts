import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { Person } from 'src/app/shared/models/person';
import { PersonService } from 'src/app/shared/services/person.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-person-famille',
  templateUrl: './person-famille.component.html',
  styleUrls: ['./person-famille.component.scss']
})
export class PersonFamilleComponent implements OnInit {

  personId?: number;
  isFormToCreatePersonByUser?: boolean;
  persons$ = new BehaviorSubject<Person[]>([]);
  displayedColumns: string[] = ['nom', 'prénom', 'date de naissance', 'actions'];
  dataSource!: MatTableDataSource<Person>;

  @ViewChild(MatPaginator, {static: true}) paginator!: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort!: MatSort;

  constructor(
    private _userService: UserService,
    private _personService: PersonService,
    private _router: Router,
    ) { }



  ngOnInit(): void {
    this.personId = this._userService.decodedToken.id;
    this.isFormToCreatePersonByUser = true;
    this.getAllpersonsCreatedByUser(this.personId);
  }

  getAllpersonsCreatedByUser(idCreateur: number | undefined) {
    if (idCreateur != undefined) {
      this._personService.getAllPersonsCreatedByUser(idCreateur).subscribe(persons => {
        this.persons$.next(persons);
        console.log("valeur du Behavior:", this.persons$.value);

        this.dataSource = new MatTableDataSource<Person>(persons);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
      });
    }
  }

  deletePerson(personId: number) {

  }

  goToAddPersonForm(){
    this._router.navigateByUrl("/person/maFamille/add")
  }
}
