import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Person } from 'src/app/shared/models/person';
import { PersonService } from 'src/app/shared/services/person.service';

@Component({
  selector: 'app-group-add-form',
  templateUrl: './group-add-form.component.html',
  styleUrls: ['./group-add-form.component.scss']
})
export class GroupAddFormComponent implements OnInit {

  groupForm!: FormGroup;
  person!: Person | null;
  message!: string;

  constructor(
    private _fb: FormBuilder,
    private _personService: PersonService,
    private _router: Router,
  ) { }

  ngOnInit(): void {
    this.groupForm = this._fb.group({
      genre: ['', [Validators.required]],
      poids: ['', [Validators.required, Validators.min(10), Validators.max(200)]],
      taille: ['', [Validators.required, Validators.min(60), Validators.max(250)]],
      activite: ['', [Validators.required]],
      objectifCalorique: ['', [Validators.required, Validators.min(0), Validators.max(200)]],
      besoinsCaloriques: [{value:'', disabled:true}],
      id: ['']
    });

    this._personService.person$.subscribe((p: Person) => {
      this.person = p;
      this.groupForm.controls['genre'].setValue(this.person.genre);
      this.groupForm.controls['poids'].setValue(this.person.poids);
      this.groupForm.controls['taille'].setValue(this.person.taille);
      this.groupForm.controls['activite'].setValue(this.person.activite);
      this.groupForm.controls['objectifCalorique'].setValue(this.person.objectifCalorique);
      this.groupForm.controls['besoinsCaloriques'].setValue(this.person.besoinsCaloriques);
      this.groupForm.controls['id'].setValue(this.person.id);
    });

  }

  submitForm() {

    if (this.groupForm.valid) {
      const p = { ...this.person, ...this.groupForm.value };
      this._personService.update(p).subscribe();
      this.message = "Modifications enregistrées avec succès !"
    }
  }

  onChangeData(){
    if (this.groupForm.valid) {
      let pers = { ...this.person };
      pers.genre = this.groupForm.value.genre;
      pers.poids = this.groupForm.value.poids;
      pers.taille = this.groupForm.value.taille;
      pers.activite = this.groupForm.value.activite;
      pers.objectifCalorique = this.groupForm.value.objectifCalorique;
      this._personService.changeData(pers as Person).subscribe();
    }
  }

  goToMainView() : void {
      setTimeout(()=> this._router.navigateByUrl("/group"), 1000);
  }

}
