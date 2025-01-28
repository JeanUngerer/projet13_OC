import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RefreshTokenService {

  token_key = 'your_car_refresh_token_key';
  rememberMe_key = 'your_car_rememberMe_key';
  private rememberMe: boolean = false;
  private refreshToken: string | null = null;


  constructor( ) {
    this.rememberMe = !!localStorage.getItem(this.rememberMe_key);
    this.refreshToken = localStorage.getItem(this.token_key) ? localStorage.getItem(this.token_key) : null;
  }


  saveRefreshToken(token: string) {
    localStorage.setItem(this.token_key, token);
  }

  getRefreshToken() {
    return localStorage.getItem(this.token_key);
  }

  deleteRefreshToken() {
    return localStorage.removeItem(this.token_key);
  }

  activateRememberMe(){
    this.rememberMe = true;
    localStorage.setItem(this.rememberMe_key, "1");
  }

  deactivateRememberMe() {
    this.rememberMe = false;
    localStorage.removeItem(this.rememberMe_key);
  }

  hasRefreshToken(){
    return !!this.refreshToken;
  }

}
