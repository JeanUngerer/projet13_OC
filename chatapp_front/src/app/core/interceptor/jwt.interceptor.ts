import {
  HttpErrorResponse, HttpEvent,
  HttpHandler,
  HttpHandlerFn,
  HttpInterceptor,
  HttpInterceptorFn,
  HttpRequest
} from "@angular/common/http";
import {inject, Injectable, OnDestroy} from "@angular/core";
import {AuthService} from "../services/auth.service";
import {JWTService} from "../services/jwt.service";
import {RefreshTokenService} from "../services/refresh-token.service";
import {Router} from "@angular/router";
import {catchError, filter, switchMap, take} from "rxjs/operators";
import {BehaviorSubject, Observable, Subject, throwError} from "rxjs";
import {log} from "@angular-devkit/build-angular/src/builders/ssr-dev-server";

@Injectable({ providedIn: 'root' })
export class JwtInterceptor implements HttpInterceptor, OnDestroy {

  private isRefreshing = false;

  private refreshingSubject: Subject<boolean> = new Subject<boolean>();


  requests: HttpRequest<any>[] = [];


  constructor(private authService: AuthService,
              private jwtService: JWTService,
              private refreshTokenService: RefreshTokenService,
              private router: Router,
              private httpHandler: HttpHandler,
              ) {
    this.refreshingSubject.subscribe( refreshStatus => { if(!refreshStatus){
      this.handleRequests();
    }})
  }

  ngOnDestroy(): void {
        this.refreshingSubject.unsubscribe();
    }

   handleRequests(){
      this.requests.forEach(

        request => {
          if (this.jwtService.hasValidToken()) {
            request = request.clone({
              setHeaders: {
                Authorization: `Bearer ${this.jwtService.getToken()}`,
              },
            });
          }
          this.httpHandler.handle(request)
        }
      )
   }

  public intercept(request: HttpRequest<any>, next: HttpHandler) {
    if (this.jwtService.hasValidToken()) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${this.jwtService.getToken()}`,
        },
      });

    }
    return next.handle(request).pipe(
      catchError((error) => {
        if (
          error instanceof HttpErrorResponse &&
          error.status === 401
        ) {
          return this.handle401Error(request, next);
        }
        if (request.url.includes('token') && error.status === 403){
          this.authService.logout();
          this.router.navigate(['/login']);
        }

        return throwError(() => error);
      })
    );
  }


  private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
    if (!this.isRefreshing) {
      this.isRefreshing = true;
      this.jwtService.saveToken('');
      if (this.refreshTokenService.hasRefreshToken()) {
        return this.authService.refreshToken().pipe(
          switchMap(() => {
            this.isRefreshing = false;
            if (this.jwtService.hasValidToken()) {
              request = request.clone({
                setHeaders: {
                  Authorization: `Bearer ${this.jwtService.getToken()}`,
                },
              });

            }
            return next.handle(request);
          }),
          catchError((error) => {
            if (error.status == '403') {
              this.authService.logout();
              this.router.navigate(['/login'])
            }
            this.isRefreshing = false;
            return throwError(() => error);
          })
        );
      } else {
        this.isRefreshing = false;
        this.router.navigate(['/login']);
      }
    }
    return next.handle(request);
  }
}
/*
export const jwtInterceptor: HttpInterceptorFn =
  (req: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>>
  => { return inject(JwtInterceptor).intercept(req, next)}
*/
