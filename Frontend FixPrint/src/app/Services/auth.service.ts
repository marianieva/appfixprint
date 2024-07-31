import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private baseUrl = 'http://localhost:8087';
  private loggedIn = new BehaviorSubject<boolean>(this.isAuthenticated());
  private userRole = new BehaviorSubject<string | null>(this.getRole());

  constructor(
    private http: HttpClient,
    private router: Router
  ) { }

  login(credentials: {username: string, password: string}): Observable<any>{
    return this.http.post<any>(`${this.baseUrl}/auth/login`, credentials)
    .pipe(map(response => {
      if(response && response.token){
        localStorage.setItem('authToken', response.token);
        localStorage.setItem('userId', response.username);
        localStorage.setItem('roles', response.roles);
      }
      return response;
    }));
  }

  signUp(datos: {nombre: string, 
                apellidos: string, 
                username: string,
                password: string,
                rol: string,
                zona: number,
                direccion: string
              }): Observable<any>{
                return this.http.post<any>(`${this.baseUrl}/auth/nuevo`, datos)
              }

  logout(): void {
    localStorage.removeItem('authToken');
    localStorage.removeItem('userId');
    localStorage.removeItem('roles');
    this.router.navigate(['']);
  }

  getToken(): string | null {
    return localStorage.getItem('authToken');
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('authToken');;
  }

  getRole(): string | null {
    return localStorage.getItem('roles');
  }

  get isLoggedIn(): Observable<boolean> {
    return this.loggedIn.asObservable();
  }

  get currentUserRole(): Observable<string | null> {
    return this.userRole.asObservable();
  }

}
