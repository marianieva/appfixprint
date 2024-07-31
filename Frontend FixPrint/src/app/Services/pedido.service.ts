import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PedidosService {

  private baseUrl = 'http://localhost:8087/pedido/todos';
  
  constructor(private http: HttpClient) { }
  
  getPedidos(idUsuario: number): Observable<any[]>{
    const url = `${this.baseUrl}/${idUsuario}`;
    console.log('URL:', url); 
    return this.http.get<any[]>(url)
    .pipe(
      catchError(this.findError)
    );
  }

  getPedidosByUser(idUsuario: number): Observable<any[]>{
    const url = `http://localhost:8087/pedido/usuario/${idUsuario}`;
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

  getPedidosAdmin(): Observable<any[]>{
    return this.http.get<any[]>('http://localhost:8087/pedido/lista')
    .pipe(
      catchError(this.findError)
    );
  }

  getPedidosRecambiosAdmin(): Observable<any[]>{
    return this.http.get<any[]>('http://localhost:8087/pedido/recambios')
    .pipe(
      catchError(this.findError)
    );
  }

  getPedidosConsumiblesAdmin(): Observable<any[]>{
    return this.http.get<any[]>('http://localhost:8087/pedido/consumibles')
    .pipe(
      catchError(this.findError)
    );
  }

  confirmarPedido(idPedido:number): Observable<any>{
    const url = `http://localhost:8087/pedido/confirmar/${idPedido}`;
    return this.http.put(url, {});
  }

  crearPedido(pedido: {
    tipoPedido: string | null,
    idUsuario: number, 
    detallesDto: {}
  }): Observable<any>{
    return this.http.post<any>(`http://localhost:8087/pedido/alta`, pedido).pipe(
      catchError(this.findError)
    );
  }

}