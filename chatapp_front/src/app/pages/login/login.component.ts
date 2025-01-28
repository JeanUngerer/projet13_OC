import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatGridListModule } from '@angular/material/grid-list';
import { Router } from '@angular/router';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {AuthService} from '../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
    imports: [
        MatCardModule,
        MatButtonModule,
        MatGridListModule,
        FormsModule,
        MatFormField,
        MatInput,
        MatLabel,
        ReactiveFormsModule
    ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  emailControl = new FormControl('', [Validators.required]);
  passwordControl = new FormControl('', Validators.required);
  submitted: boolean = false;
  loginForm = new FormGroup({
    email: this.emailControl,
    password: this.passwordControl,
  });
  constructor(
    public router: Router,
    private authService: AuthService,
    ) {
  }

  get email() {
    return this.loginForm.get('email')?.value;
  }
  get password() {
    return this.loginForm.get('password')?.value;
  }

  login(){
    if (this.loginForm.valid) {
      this.authService.login({
        username: this.email ? this.email : '',
        password: this.password ? this.password : '',
      }).subscribe({
        next:(r) => {this.handleAuthSuccess();},
        error:(err) => this.handleAuthError(err)}
      );
    }
  }
  handleAuthError(err: any) {
    console.error(('login.error_login'))
  }

  handleAuthSuccess() {
    this.router.navigate(['']);
  }
}
