import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AlimentAllComponent } from './components/aliment/aliment-all/aliment-all.component';
import { AlimentDetailsComponent } from './components/aliment/aliment-details/aliment-details.component';
import { AuthComponent } from './components/auth/auth.component';
import { ResetPasswordFormComponent } from './components/auth/reset-password-form/reset-password-form.component';
import { HomeComponent } from './components/home/home.component';
import { PersonFamilleAddFormComponent } from './components/person/person-famille/person-famille-add-form/person-famille-add-form.component';
import { PersonFamilleComponent } from './components/person/person-famille/person-famille.component';
import { PersonIdentityComponent } from './components/person/person-identity/person-identity.component';
import { PersonMorphologyComponent } from './components/person/person-morphology/person-morphology.component';
import { PersonComponent } from './components/person/person.component';
import { ItemPlatComponent } from './components/plats/item-plat/item-plat.component';
import { PersonPlatsComponent } from './components/plats/person-plats/person-plats.component';
import { AuthGuard } from './shared/guards/auth.guard';

const routes: Routes = [
  {
    path: "person" ,
    canActivate:[AuthGuard],
    component: PersonComponent,
    children: [
      { path: "identity", component: PersonIdentityComponent },
      { path: "morphology", component: PersonMorphologyComponent },
      { path: "plats", component: PersonPlatsComponent },
      { path: "maFamille", component: PersonFamilleComponent },
      { path: "maFamille/add", component: PersonFamilleAddFormComponent }

    ],
  },
  {
    path:"aliments",
    children: [
      { path: ":id", component: AlimentAllComponent },
      { path: ":pId/:aId", component: AlimentDetailsComponent }
    ],
  },
  { path: "plat",
    canActivate:[AuthGuard],
    children: [
      { path: ":id", component: ItemPlatComponent }
    ]},

  { path: "home" , component: HomeComponent },
  { path: "signin", component: AuthComponent },
  { path: "login", component: AuthComponent },
  { path: "resetPassword", component: ResetPasswordFormComponent},
  { path: "**", redirectTo: "home" }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
