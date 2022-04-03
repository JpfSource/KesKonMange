import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { of, switchMap } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  respService!: boolean;
  constructor(
    private _authService : AuthService,
    private _router: Router,
    ){}

  canActivate() {
    return this._authService.checkIfUserIsConnected()
      .pipe(
        switchMap(logged => {
          if(logged) return this._authService.isLoggedIn$;
          this._router.navigateByUrl("/login");
          return of(false);
        })
      );
  }

}
