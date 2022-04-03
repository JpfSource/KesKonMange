import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Person } from 'src/app/shared/models/person';
import { AuthService } from 'src/app/shared/services/auth.service';
import { PersonService } from 'src/app/shared/services/person.service';

@Component({
  selector: 'app-board-user',
  templateUrl: './board-user.component.html',
  styleUrls: ['./board-user.component.scss']
})
export class BoardUserComponent implements OnInit {
  persons!: Person[];

  constructor(
    private personService: PersonService,
    private authService : AuthService,
    private _router: Router,
  ) { }

  ngOnInit(): void {
    this.personService.getPersonAll().subscribe({
     next: (persons => {
      this.persons = persons;
    }),
    error: err => {
      this.persons = JSON.parse(err.error).message;
    }
  });
  }

  logout(){
    this.authService.logout();
    this._router.navigateByUrl('/login');
  }

}
