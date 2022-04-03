import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Person } from 'src/app/shared/models/person';
import { PersonService } from 'src/app/shared/services/person.service';


@Component({
  selector: 'app-person-morphology',
  templateUrl: './person-morphology.component.html',
  styleUrls: ['./person-morphology.component.scss']
})
export class PersonMorphologyComponent implements OnInit {

  morphologyForm!: FormGroup;
  person!: Person | null;
  message!: string;

  constructor(
    private _fb: FormBuilder,
    private _personService: PersonService,
    private _router: Router,
  ) { }

  ngOnInit(): void {
    this.morphologyForm = this._fb.group({
      genre: ['', [Validators.required]],
      poids: ['', [Validators.required, Validators.min(10), Validators.max(200)]],
      taille: ['', [Validators.required, Validators.min(60), Validators.max(250)]],
      activite: ['', [Validators.required]],
      objectifCalorique: ['', [Validators.required, Validators.min(0), Validators.max(200)]],
      besoinsCaloriques: [''],
      id: ['']
    });

    this._personService.person$.subscribe((p: Person) => {
      this.person = p;
      this.morphologyForm.controls['genre'].setValue(this.person.genre);
      this.morphologyForm.controls['poids'].setValue(this.person.poids);
      this.morphologyForm.controls['taille'].setValue(this.person.taille);
      this.morphologyForm.controls['activite'].setValue(this.person.activite);
      this.morphologyForm.controls['objectifCalorique'].setValue(this.person.objectifCalorique);
      this.morphologyForm.controls['besoinsCaloriques'].setValue(this.person.besoinsCaloriques);
      this.morphologyForm.controls['id'].setValue(this.person.id);
    });

  }

  submitForm() {

    if (this.morphologyForm.valid) {
      const p = { ...this.person, ...this.morphologyForm.value };
      this._personService.update(p).subscribe();
      this.message = "Modifications enregistrées avec succès !"
    }
  }

  onChangeData(){
    if (this.morphologyForm.valid) {
      let pers = { ...this.person };
      pers.genre = this.morphologyForm.value.genre;
      pers.poids = this.morphologyForm.value.poids;
      pers.taille = this.morphologyForm.value.taille;
      pers.activite = this.morphologyForm.value.activite;
      pers.objectifCalorique = this.morphologyForm.value.objectifCalorique;
      this._personService.changeData(pers as Person).subscribe();
    }
  }

  goToMainView() : void {
    setTimeout(()=> this._router.navigateByUrl("/person"), 1000);
  }
}
