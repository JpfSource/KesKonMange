import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthComponent } from './components/auth/auth.component';
import { FooterComponent } from './components/footer/footer.component';
import { HeaderComponent } from './components/header/header.component';
import { HomeComponent } from './components/home/home.component';
import { PersonIdentityComponent } from './components/person/person-identity/person-identity.component';
import { PersonMorphologyComponent } from './components/person/person-morphology/person-morphology.component';
import { PersonComponent } from './components/person/person.component';
import { ItemPlatComponent } from './components/plats/item-plat/item-plat.component';
import { PersonPlatsComponent } from './components/plats/person-plats/person-plats.component';
import { BoardUserComponent } from './components/user/board-user/board-user.component';
import { authInterceptorProviders } from './shared/helpers/auth.interceptor';
import { CriteriaComponent } from './shared/criteria/criteria.component';
import { NavComponent } from './components/nav/nav.component';



@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    PersonComponent,
    PersonMorphologyComponent,
    PersonIdentityComponent,
    HomeComponent,
    BoardUserComponent,
    AuthComponent,
    PersonPlatsComponent,
    ItemPlatComponent,
    CriteriaComponent,
    NavComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
