import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PersonService } from 'src/app/shared/services/person.service';
import { Person } from 'src/app/shared/models/person';
import { UserService } from 'src/app/shared/services/user.service';
import { AuthService } from 'src/app/shared/services/auth.service';
import {MatMenuModule} from '@angular/material/menu';
import {MatIconModule} from '@angular/material/icon'

@Component({
  selector: 'app-person',
  templateUrl: './person.component.html',
  styleUrls: ['./person.component.scss']
})
export class PersonComponent implements OnInit {

  public person?: Person | null;

  constructor(
    private _personService : PersonService,
    private _route: ActivatedRoute,
    private _router : Router,
    private _userService: UserService,
    private _authService : AuthService,

  ) { }

  getPrenomNom(){
    return this.person?.prenom +" "+ this.person?.nom?.toUpperCase();
  }

  ngOnInit(): void {
    this._userService.updateToken();
    this._route.paramMap.subscribe(param => {
      const personId = this._userService.decodedToken.id;
      if(personId != null && personId > 0) {
        this._personService.getPersonById(personId)
        .subscribe((p: Person | null) => {
          this.person = p;
        });
      }
      else {
        this._router.navigateByUrl("/home");
      }
    });
  }

}
