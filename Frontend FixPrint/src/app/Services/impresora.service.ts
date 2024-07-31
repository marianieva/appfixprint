import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ImpresoraService {
  
  constructor(private http: HttpClient) { }
  
  getEquipos(idUsuario: number): Observable<any[]>{
    const url = `http://localhost:8087/impresora/cliente/${idUsuario}`;
    return this.http.get<any[]>(url)
    .pipe(
      catchError(this.findError)
    );
  }

  getEquiposLista(): Observable<any[]>{
    return this.http.get<any[]>('http://localhost:8087/impresora/lista')
    .pipe(
      catchError(this.findError)
    );
  }

  getEquiposCliente(userId : number): Observable<any[]>{
    return this.http.get<any[]>(`http://localhost:8087/impresora/cliente/${userId}`)
    .pipe(
      catchError(this.findError)
    );
  }

  newEquipo(datos: {
    marca: string, 
    modelo: string,
    usuario: number,
    serialNumber: number
  }): Observable<any>{
    return this.http.post<any>(`http://localhost:8087/impresora/alta`, datos).pipe(
      catchError(this.findError)
    );
  }
  
  private findError(error: HttpErrorResponse) {
    console.error('Ha ocurrido un error:', error);
    return throwError('Error al recuperar los datos; inténtelo de nuevo más tarde.');
  }

}
