import {inject, Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot} from "@angular/router";
import {AuthService} from "../services/auth.service";
import {RefreshTokenService} from "../services/refresh-token.service";
import {catchError, switchMap} from "rxjs/operators";
import {tap, throwError} from "rxjs";


@Injectable({providedIn: 'root'})
class NoPermissionsGuard {

  constructor(
    private router: Router,
    private authService: AuthService,
    private refreshTokenService: RefreshTokenService
  ) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['']);
      return false;
    }
    if (this.refreshTokenService.hasRefreshToken()) {
      let outcome = true;
      this.authService.refreshToken().subscribe({
        next: (body) => {
          this.router.navigate([''])
          outcome = false;
          return outcome;
        },
        error: (error) => {
          if (error.status == '401') {
            this.authService.logout();
          }
          outcome = true;
          return outcome;
        }
    });

    }
    return true;
  }
}

export const NoAuthGuard: CanActivateFn = (next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean => {
  return inject(NoPermissionsGuard).canActivate(next, state);
}
