import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable, tap, throwError} from 'rxjs';
import { catchError } from 'rxjs/operators';
import {JWTService} from "./jwt.service";


@Injectable({
  providedIn: 'root',
})
export class ApiService {

    apiUrl = "http://localhost:8087";

  constructor(private http: HttpClient,
              private jwtSerice: JWTService,
            ) {}

  /*
  This method is used for handling error responses from the API.
  */
  private formatErrors(error: any) {
    console.error(error)
    return throwError(() => Error((error).message));
  }

  /*
  This method is used to send a GET request to the API.
  The request is sent to the provided path.
  */
  get(path: string): Observable<any> {
    return this.http
      .get(`${this.apiUrl}/api${path}`)
      .pipe(tap({next: res => {}, error: error => {this.formatErrors(error)} }));
  }

  getCV(path: string): Observable<any> {
    return this.http
      .get(`${this.apiUrl}/api${path}`, {responseType: 'blob'})
      .pipe(catchError(this.formatErrors));
  }

  getImage(path: string) {
    return this.http.get(`${this.apiUrl}/api/images/${path}`, { observe: 'response', responseType: 'blob' as 'json' });
  }

  /*
  This method is used to send a GET request to the API without requiring authentication.
  The request is sent to the provided path.
  */
  getNoAuth(path: string): Observable<any> {
    return this.http
      .get(`${this.apiUrl}/api${path}`)
      .pipe(catchError(err =>this.formatErrors(err)));
  }

  /*
  This method is used to send a PUT request to the API.
  The request is sent to the provided path with the provided body as the payload.
  */
  put(path: string, body: Object = {}): Observable<any> {
    return this.http
      .put(`${this.apiUrl}/api${path}`, JSON.stringify(body),
      {
        headers: new HttpHeaders({ 'content-type': 'application/json' })
      })
      .pipe(catchError(err =>this.formatErrors(err)));
  }

  /*
  This method is used to send a POST request to the API.
  The request is sent to the provided path with the provided body as the payload.
  */
  post(path: string, body: Object = {}): Observable<any> {
    return this.http
      .post(`${this.apiUrl}/api${path}`, JSON.stringify(body),
      {
        headers: new HttpHeaders({ 'content-type': 'application/json' })
      })
      .pipe(catchError(err =>this.formatErrors(err)));
  }

  /*
  This method is used to send a POST request to the API.
  The request is sent to the provided path with the provided formData as the payload.
  */
  postFormData(path: string, body: FormData = new FormData()): Observable<any> {
    return this.http
      .post(`${this.apiUrl}/api${path}`, body)
      .pipe(catchError(err =>this.formatErrors(err)));
  }

  /*
  This method is used to send a PUT request to the API.
  The request is sent to the provided path with the provided formData as the payload.
  */
  putFormData(path: string, body: FormData = new FormData()): Observable<any> {
    return this.http
      .put(`${this.apiUrl}/api${path}`, body)
      .pipe(catchError(err =>this.formatErrors(err)));
  }

  /*
  This method is used to send a DELETE request to the API.
  The request is sent to the provided path.
  */
  delete(path: string): Observable<any> {
    return this.http
      .delete(`${this.apiUrl}/api${path}`)
      .pipe(catchError(err =>this.formatErrors(err)));
  }
}
