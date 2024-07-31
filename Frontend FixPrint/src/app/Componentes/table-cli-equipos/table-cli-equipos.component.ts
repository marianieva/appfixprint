import { Component, OnInit, AfterViewChecked, ViewChildren, QueryList, ElementRef } from '@angular/core';
import { ImpresoraService } from '../../Services/impresora.service';

@Component({
  selector: 'app-table-cli-equipos',
  templateUrl: './table-cli-equipos.component.html',
  styleUrls: ['./table-cli-equipos.component.css']
})
export class TableCliEquiposComponent implements OnInit {

  equipos: any[] = []; // Para cargar el array con el JSON que devuelva la petición GET
  loading = true; // Para mostrar mensaje de carga de datos en el HTML
  error = ''; // Para manejar los errores en la carga de datos de la petición GET
  idUsuario: number = Number(localStorage.getItem('userId'));

  @ViewChildren('btnPedido') btnPedido!: QueryList<ElementRef>;

  constructor(public service: ImpresoraService) { }

  ngOnInit(): void {
    this.getEquipos(this.idUsuario);
  }

  getEquipos(idUsuario:number) {
    this.loading= true;
    this.service.getEquipos(idUsuario).subscribe({
      next: (data: any[]) => {
        this.equipos = data;
        this.loading = false; // Finaliza la carga
      },
      error: (err) => {
        this.error = err; // Asigna el mensaje de error
        this.loading = false; // Finaliza la carga
      }
    });
  }
}