import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Person } from 'src/app/shared/models/person';
import { PersonService } from 'src/app/shared/services/person.service';

@Component({
  selector: 'app-person-identity',
  templateUrl: './person-identity.component.html',
  styleUrls: ['./person-identity.component.scss']
})
export class PersonIdentityComponent implements OnInit {

  identityForm!: FormGroup;
  person!: Person | null;
  message!: string;

  constructor(
    private _fb: FormBuilder,
    private _personService: PersonService,
    private _router: Router,
  ) { }

  ngOnInit(): void {
    this.identityForm = this._fb.group({
      nom: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      prenom: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      description: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      dateNaissance: ['', Validators.required],
      id: ['']
    });

    this._personService.person$.subscribe((p: Person) => {
      this.person = p;
      this.identityForm.controls['nom'].setValue(this.person.nom);
      this.identityForm.controls['prenom'].setValue(this.person?.prenom);
      this.identityForm.controls['description'].setValue(this.person?.description);
      this.identityForm.controls['dateNaissance'].setValue(this.person?.dateNaissance);
      this.identityForm.controls['id'].setValue(this.person.id);
    });
  }

  submitForm() {
    if (this.identityForm.valid) {
      this._personService.update({ ...this.person, ...this.identityForm.value }).subscribe();
      this.message = "Modifications enregistrées avec succès !"
    }
  }

  goToMainView(): void {
    setTimeout(()=> this._router.navigateByUrl("/person"), 1000);
  }

}
