import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  private baseUrl = 'http://localhost:8087/productos/';

  constructor(private http: HttpClient) { }

  getProductosAlmacen(idUsuario: number): Observable<any[]>{
    const url = `http://localhost:8087/tecnico/productos/${idUsuario}`;
    console.log('URL:', url); 
    return this.http.get<any[]>(url)
    .pipe(
      catchError(this.findError)
    );
  }
  
  private findError(error: HttpErrorResponse) {
    console.error('Error al recuperar los datos:', error.message);
    console.error('Detalles del error:', error);
    return throwError('Error al recuperar los datos; inténtelo de nuevo más tarde.');
  }

  getProductosPorMarcaTecnico(marca:string): Observable<any[]>{
    const url = `http://localhost:8087/productos/lista/repuestos/${marca}`;
    return this.http.get<any[]>(url)
    .pipe(
      catchError(this.findError)
    );
  }

  getProductosPorMarcaCliente(marca:string): Observable<any[]>{
    const url = `http://localhost:8087/productos/lista/consumibles/${marca}`;
    return this.http.get<any[]>(url)
    .pipe(
      catchError(this.findError)
    );
  }

}
