import { Component, OnInit, AfterViewChecked } from '@angular/core';
import { IncidenciaService } from '../../Services/incidencia.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-table-admin-incidencias-fin',
  templateUrl: './table-admin-incidencias-fin.component.html',
  styleUrls: ['./table-admin-incidencias-fin.component.css'],
  providers: [DatePipe]
})
export class TableAdminIncidenciasFinComponent implements OnInit{

  incidencias: any[] = []; // Para cargar el array con el JSON que devuelva la petición GET
  loading = true; // Para mostrar mensaje de carga de datos en el HTML
  error = ''; // Para manejar los errores en la carga de datos de la petición GET
  currentView: 'all' | 'finalized' | 'pending' = 'all';
  finalized: any;

  constructor(public service: IncidenciaService, private datePipe: DatePipe) { }
  
  ngOnInit(): void {
    this.getIncidencias();
  }

  getIncidencias() {
    this.loading= true;
    this.service.getIncidencias().subscribe({
      next: (data: any[]) => {
        this.incidencias = data.map(incidencia => {
          incidencia.fechaAlta = this.datePipe.transform(incidencia.fechaAlta, 'dd-MM-yyyy HH:mm');
          incidencia.fechaInicio = this.datePipe.transform(incidencia.fechaInicio, 'dd-MM-yyyy HH:mm');
          incidencia.fechaFin = this.datePipe.transform(incidencia.fechaFin, 'dd-MM-yyyy HH:mm');
          return incidencia;
        });
        this.loading = false; // Finaliza la carga
      },
      error: (err) => {
        this.error = err; // Asigna el mensaje de error
        this.loading = false; // Finaliza la carga
      }
    });
  }

  getIncidenciasFinAdmin() {
    this.loading= true;
    this.service.getIncidenciasFinAdmin().subscribe({
      next: (data: any[]) => {
        this.incidencias = data.map(incidencia => {
          incidencia.fechaAlta = this.datePipe.transform(incidencia.fechaAlta, 'dd-MM-yyyy HH:mm');
          incidencia.fechaInicio = this.datePipe.transform(incidencia.fechaInicio, 'dd-MM-yyyy HH:mm');
          incidencia.fechaFin = this.datePipe.transform(incidencia.fechaFin, 'dd-MM-yyyy HH:mm');
          return incidencia;
        });
        this.loading = false; // Finaliza la carga
      },
      error: (err) => {
        this.error = err; // Asigna el mensaje de error
        this.loading = false; // Finaliza la carga
      }
    });
  }

  getIncidenciasPenAdmin() {
    this.loading= true;
    this.service.getIncidenciasPenAdmin().subscribe({
      next: (data: any[]) => {
        this.incidencias = data.map(incidencia => {
          incidencia.fechaAlta = this.datePipe.transform(incidencia.fechaAlta, 'dd-MM-yyyy HH:mm');
          incidencia.fechaInicio = this.datePipe.transform(incidencia.fechaInicio, 'dd-MM-yyyy HH:mm');
          incidencia.fechaFin = this.datePipe.transform(incidencia.fechaFin, 'dd-MM-yyyy HH:mm');
          return incidencia;
        });
        this.loading = false; // Finaliza la carga
      },
      error: (err) => {
        this.error = err; // Asigna el mensaje de error
        this.loading = false; // Finaliza la carga
      }
    });
  }

  changeView(view: 'all' | 'finalized' | 'pending') {
    this.currentView = view;
    if (view === 'all') {
      this.getIncidencias();
    } else if (view === 'finalized') {
      this.getIncidenciasFinAdmin();
    } else if (view === 'pending') {
      this.getIncidenciasPenAdmin();
    }
  }

  cancelarIncidencia(idIncidenia: number) {
    this.service.cancelarIncidencia(idIncidenia).subscribe({
      next: response => {
      },
      error: (err) => {
        this.error = err;
      }
    });
  }

}
