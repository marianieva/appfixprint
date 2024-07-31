import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable, throwError, of } from 'rxjs';
import { catchError, map, switchMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class IncidenciaService {

  constructor(private http: HttpClient) { }

  getIncidencias(): Observable<any[]>{
    return this.http.get<any[]>('http://localhost:8087/incidencia/lista')
    .pipe(
      catchError(this.findError)
    );
  }
  
  getIncidenciasFinAdmin(): Observable<any[]>{
    return this.http.get<any[]>('http://localhost:8087/incidencia/finalizdas')
    .pipe(
      catchError(this.findError)
    );
  }

  getIncidenciasPenAdmin(): Observable<any[]>{
    return this.http.get<any[]>('http://localhost:8087/incidencia/pendientes')
    .pipe(
      catchError(this.findError)
    );
  }
  
  cancelarIncidencia(idIncidencia: number): Observable<any> {
    const url = `http://localhost:8087/incidencia/cancelar/${idIncidencia}`;
    return this.http.put(url, {});
  }

  iniciarIncidencia(idIncidencia: number): Observable<any> {
    const url = `http://localhost:8087/incidencia/iniciar/${idIncidencia}`;
    return this.http.put(url, {});
  }

  newIncidenciaAdmin(datos: {
    usuario: number, 
    serialNumber: number,
    descripcionCliente: string,
    listaProductos: any[];
  }): Observable<any>{
    return this.http.post<any>(`http://localhost:8087/incidencia/alta`, datos).pipe(
      catchError(this.findError)
    );
  }
  
  finalizarIncidencia(datos: {
    idIncidencia: number, 
    comentarioTecnico: string,
    productosEnIncidencia: any[];
  }): Observable<any>{
    return this.http.put<any>(`http://localhost:8087/incidencia/cerrar`, datos).pipe(
      catchError(this.findError)
    );
  }
  
  getIncidenciasFinalizadas(idUsuario: number): Observable<any[]>{
    return this.http.get<any[]>(`http://localhost:8087/incidencia/usuario/${idUsuario}`)
    .pipe(
      switchMap(incidencias =>
        this.http.get<any[]>('http://localhost:8087/incidencia/finalizdas').pipe(
          map(finalizadas => {
            const finalizadasIds = new Set(finalizadas.map(f => f.idIncidencia));
            const incidenciasFinalizadas = incidencias.filter(i => finalizadasIds.has(i.idIncidencia));
            return incidenciasFinalizadas;
          })
        )
      ),
      catchError(this.findError)
    );
  }

  getIncidenciasPendientes(idUsuario: number): Observable<any[]>{
    return this.http.get<any[]>(`http://localhost:8087/incidencia/usuario/${idUsuario}`)
    .pipe(
      switchMap(incidencias =>
        this.http.get<any[]>('http://localhost:8087/incidencia/pendientes').pipe(
          map(finalizadas => {
            const finalizadasIds = new Set(finalizadas.map(f => f.idIncidencia));
            const incidenciasFinalizadas = incidencias.filter(i => finalizadasIds.has(i.idIncidencia));
            return incidenciasFinalizadas;
          })
        )
      ),
      catchError(this.findError)
    );
  }

  getIncidenciasEnCurso(idUsuario: number): Observable<any[]>{
    return this.http.get<any[]>(`http://localhost:8087/incidencia/tecnico/${idUsuario}`)
    .pipe(
      switchMap(incidencias =>
        this.http.get<any[]>('http://localhost:8087/incidencia/encurso').pipe(
          map(finalizadas => {
            const finalizadasIds = new Set(finalizadas.map(f => f.idIncidencia));
            const incidenciasFinalizadas = incidencias.filter(i => finalizadasIds.has(i.idIncidencia));
            return incidenciasFinalizadas;
          })
        )
      ),
      catchError(this.findError)
    );
  }

  getIncidenciasPendientesTecnico(idUsuario: number): Observable<any[]>{
    return this.http.get<any[]>(`http://localhost:8087/incidencia/tecnico/${idUsuario}`)
    .pipe(
      switchMap(incidencias =>
        this.http.get<any[]>('http://localhost:8087/incidencia/pendientes').pipe(
          map(finalizadas => {
            const finalizadasIds = new Set(finalizadas.map(f => f.idIncidencia));
            const incidenciasFinalizadas = incidencias.filter(i => finalizadasIds.has(i.idIncidencia));
            return incidenciasFinalizadas;
          })
        )
      ),
      catchError(this.findError)
    );
  }

  getIncidenciasFinalizadasTecnico(idUsuario: number): Observable<any[]>{
    return this.http.get<any[]>(`http://localhost:8087/incidencia/tecnico/${idUsuario}`)
    .pipe(
      switchMap(incidencias =>
        this.http.get<any[]>('http://localhost:8087/incidencia/finalizdas').pipe(
          map(finalizadas => {
            const finalizadasIds = new Set(finalizadas.map(f => f.idIncidencia));
            const incidenciasFinalizadas = incidencias.filter(i => finalizadasIds.has(i.idIncidencia));
            return incidenciasFinalizadas;
          })
        )
      ),
      catchError(this.findError)
    );
  }

  getincidenciasByUser(idUsuario:number): Observable<any[]> {
      const url = `http://localhost:8087/incidencia/usuario/${idUsuario}`;
      return this.http.get<any[]>(url, {})
      .pipe(
        catchError(this.findError)
      );
  }

  getincidenciasByTecnico(idUsuario:number): Observable<any[]> {
    const url = `http://localhost:8087/incidencia/tecnico/${idUsuario}`;
    return this.http.get<any[]>(url, {})
    .pipe(
      catchError(this.findError)
    );
}
  

  private findError(error: HttpErrorResponse) {
    console.error('Ha ocurrido un error:', error);
    return throwError('Error al recuperar los datos; inténtelo de nuevo más tarde.');
  }
  
}
