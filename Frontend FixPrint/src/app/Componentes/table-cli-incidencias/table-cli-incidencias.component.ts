import { Component, OnInit, AfterViewChecked, ViewChildren, QueryList, ElementRef } from '@angular/core';
import { IncidenciaService } from '../../Services/incidencia.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-table-cli-incidencias',
  templateUrl: './table-cli-incidencias.component.html',
  styleUrls: ['./table-cli-incidencias.component.css'],
  providers: [DatePipe]
})
export class TableCliIncidenciasComponent implements OnInit, AfterViewChecked {

  incidencias: any[] = []; // Para cargar el array con el JSON que devuelva la petición GET
  loading = true; // Para mostrar mensaje de carga de datos en el HTML
  error = ''; // Para manejar los errores en la carga de datos de la petición GET

  @ViewChildren('btnCancelar') btnCancelar!: QueryList<ElementRef>;

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

  ngAfterViewChecked() {
    // Asociar evento click a cada botón después de que se inicialice la vista
    this.btnCancelar.forEach(button => {
      // Primero, eliminar cualquier evento click existente
      button.nativeElement.removeEventListener('click', this.handleButtonClick);

      // Luego, agregar el nuevo evento click
      button.nativeElement.addEventListener('click', this.handleButtonClick.bind(this));
    });
  }

  handleButtonClick(event: Event) {
    const target = event.target as HTMLElement;
    const idIncidencia = target.getAttribute('data-id');
    if (idIncidencia) {
      this.cancelarIncidencia(parseInt(idIncidencia, 10));
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