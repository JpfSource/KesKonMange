import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { TokenStorageService } from './token-storage.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  token!: any;
  helper = new JwtHelperService();
  decodedToken : any;
  expirationDate : any;
  isExpired : any;

  constructor(private tokenStorage: TokenStorageService) {
    this.updateToken();
   }


  updateToken(){
    this.token = this.tokenStorage.getToken()
    this.decodedToken = this.helper.decodeToken(this.token!);
    this.expirationDate = this.helper.getTokenExpirationDate(this.token!);
    this.isExpired = this.helper.isTokenExpired(this.token!);
  }

}

