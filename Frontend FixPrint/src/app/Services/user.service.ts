import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { forkJoin } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userRole: string;

  constructor(private http: HttpClient) {
    // Obtén el rol del localStorage y maneja el caso en que pueda ser null
    const role = localStorage.getItem('roles');
    this.userRole = role ? role : 'ROL_INVITADO'; // Default role if none found
  }

 getUserRole(): string {
   return this.userRole;
 }

 getUser(userId: number): Observable<any[]> {
  return this.http.get<any[]>(`http://localhost:8087/cliente/uno/${userId}`)
  .pipe(
    catchError(this.findError)
  );
 }
  
 private findError(error: HttpErrorResponse) {
  console.error('Error al recuperar los datos:', error.message);
  console.error('Detalles del error:', error);
  return throwError('Error al recuperar los datos; inténtelo de nuevo más tarde.');
}

getUsuariosAdmin(): Observable<any[]> {
  const clientes$ = this.http.get<any[]>('http://localhost:8087/cliente/lista');
  const tecnicos$ = this.http.get<any[]>('http://localhost:8087/tecnico/lista');

  return forkJoin([clientes$, tecnicos$]).pipe(
    map((results: any[][]) => {
      const [clientes, tecnicos] = results;
      return [...clientes, ...tecnicos];
    }),
    catchError(this.findError)
  );
}

getTecnicosAdmin(): Observable<any[]>{
  return this.http.get<any[]>('http://localhost:8087/tecnico/lista')
  .pipe(
    catchError(this.findError)
  );
}

getClientesAdmin(): Observable<any[]>{
  return this.http.get<any[]>('http://localhost:8087/cliente/lista')
  .pipe(
    catchError(this.findError)
  );
}

getRoles(): Observable<any[]>{
  return this.http.get<any[]>('http://localhost:8087/roles/lista')
  .pipe(
    catchError(this.findError)
  );
}

getZonas(): Observable<any[]>{
  return this.http.get<any[]>('http://localhost:8087/zona/lista')
  .pipe(
    catchError(this.findError)
  );
}

updateUser(datos: {
  idUsuario: number,
  username: string,
  nombre: string, 
  apellidos: string, 
  direccion: string
}): Observable<any>{
  return this.http.post<any>(`http://localhost:8087/cliente/modificar`, datos).pipe(
    catchError(this.findError)
  );
}

 
}