import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Person } from 'src/app/shared/models/person';
import { PersonService } from 'src/app/shared/services/person.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-person-famille-add-form',
  templateUrl: './person-famille-add-form.component.html',
  styleUrls: ['./person-famille-add-form.component.scss',
              '../../person-morphology/person-morphology.component.scss']
})
export class PersonFamilleAddFormComponent implements OnInit {

  personAddForm!: FormGroup;
  person?: Person | null;
  message?: string;
  personAdd?: Person;
  personId?: number;

  constructor(
    private _fb: FormBuilder,
    private _personService: PersonService,
    private _router: Router,
    private _userService: UserService
  ) { }

  ngOnInit(): void {
    this.personAddForm = this._fb.group({
      genre: ['', [Validators.required]],
      nom: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      prenom: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      description: ['', [Validators.minLength(2), Validators.maxLength(100)]],
      dateNaissance: ['', Validators.required],
      poids: ['', [Validators.required, Validators.min(10), Validators.max(200)]],
      taille: ['', [Validators.required, Validators.min(60), Validators.max(250)]],
      activite: ['', [Validators.required]],
      objectifCalorique: ['', [Validators.required, Validators.min(0), Validators.max(200)]],
      id: ['']
    });

    this.personId = this._userService.decodedToken.id;
  }

  submitForm() {
    if (this.personAddForm.valid) {
      const pers = { ...this.person, ...this.personAddForm.value };
      this._personService.createPersonByUser(pers, this.personId).subscribe(p => this.personAdd = p);
      setTimeout(() => {
        this.message = "Personne enregistrée avec succès !"
      }, 1500);

    }
  }

  goToMainView() : void {
      setTimeout(()=> this._router.navigateByUrl("/person/maFamille"), 1500);
  }

}
