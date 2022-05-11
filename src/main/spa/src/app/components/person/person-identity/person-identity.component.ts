import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Person } from 'src/app/shared/models/person';
import { PersonService } from 'src/app/shared/services/person.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-person-identity',
  templateUrl: './person-identity.component.html',
  styleUrls: ['./person-identity.component.scss']
})
export class PersonIdentityComponent implements OnInit {

  @Input()
  isFormToCreatePersonByUser?: boolean = false ;

  identityForm!: FormGroup;
  person!: Person | null;
  personAdd?: Person;
  message!: string;
  personId?: number;

  constructor(
    private _fb: FormBuilder,
    private _personService: PersonService,
    private _router: Router,
    private _userService: UserService
  ) { }

  ngOnInit(): void {
    this.identityForm = this._fb.group({
      nom: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      prenom: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      description: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      dateNaissance: ['', Validators.required],
      id: ['']
    });
    if(this.isFormToCreatePersonByUser == false) {
      this._personService.person$.subscribe((p: Person) => {
        this.person = p;
        this.identityForm.controls['nom'].setValue(this.person.nom);
        this.identityForm.controls['prenom'].setValue(this.person?.prenom);
        this.identityForm.controls['description'].setValue(this.person?.description);
        this.identityForm.controls['dateNaissance'].setValue(this.person?.dateNaissance);
        this.identityForm.controls['id'].setValue(this.person.id);
      });
    }
    this.personId = this._userService.decodedToken.id;
    console.log(this.personId);

  }

  submitForm() {
    if (this.identityForm.valid && this.isFormToCreatePersonByUser == false) {
      this._personService.update({ ...this.person, ...this.identityForm.value }).subscribe();
      this.message = "Modifications enregistrées avec succès !"
    }
    if (this.identityForm.valid && this.isFormToCreatePersonByUser == true) {
      console.log("Dans le submoiForm de AddedForm");
      const pers = { ...this.person, ...this.identityForm.value };
      this._personService.createPersonByUser(pers, this.personId).subscribe(p => this.personAdd = p);
      this.message = "Données enregistrées avec succès !"
    }
  }

  goToMainView(): void {
    this.submitForm();
    if (!this.isFormToCreatePersonByUser) {
      setTimeout(()=> this._router.navigateByUrl("/person"), 1000);
    } else {
      // setTimeout(()=> this._router.navigateByUrl("/morphology"), 1000);
    }

  }

}
