import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, AbstractControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Person } from 'src/app/shared/models/person';
import { AuthService } from 'src/app/shared/services/auth.service';
import { TokenStorageService } from 'src/app/shared/services/token-storage.service';

@Component({
  selector: 'app-reset-password-form',
  templateUrl: './reset-password-form.component.html',
  styleUrls: ['./reset-password-form.component.scss',
              '../auth.component.scss']
})
export class ResetPasswordFormComponent implements OnInit {
  resetPasswordForm!: FormGroup;
  person?: Person | null;
  hide : boolean = true;
  message!: string;
  error!: string;

  constructor(
    private _authService: AuthService,
    private _fb: FormBuilder,
    private _route: ActivatedRoute,
    private _router: Router,
    private _tokenStorage: TokenStorageService
  ) { }

  ngOnInit(): void {
    this.resetPasswordForm = this._fb.group({
      email: ['', [Validators.required, Validators.email]],
      pwd: ['', [Validators.required, Validators.minLength(4)]],
    });
  }

  submitForm(): void {
    this._tokenStorage.saveToken("");
    if (this.resetPasswordForm.valid) {
      const p = { ...this.person, ...this.resetPasswordForm.value }
        this._authService
          .resetPassword(p)
          .subscribe({
            next: (data => {
              this.message = "Inscription réussie !"
              this.goToLogin();
            }),
            error: err => {
              this.error = err.error;
            }
          });

      }
    }


  hasErrors(control: AbstractControl | null, key: string) {
    if (control && control.errors) {
      return control.errors[key];
    }
    return null;
  }

  /**
   * Méthode qui permet d'aller à la page Login après inscription.
   */
  goToLogin() : void {
    setTimeout(()=> this._router.navigateByUrl('/login'), 1500);
  }

}
