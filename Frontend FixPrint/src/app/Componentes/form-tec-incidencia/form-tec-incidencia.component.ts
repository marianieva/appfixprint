import { Component, OnInit } from '@angular/core';
import { IncidenciaService } from 'src/app/Services/incidencia.service';
import { ProductoService } from 'src/app/Services/producto.service';

@Component({
  selector: 'app-form-tec-incidencia',
  templateUrl: './form-tec-incidencia.component.html',
  styleUrls: ['./form-tec-incidencia.component.css']
})
export class FormTecIncidenciaComponent implements OnInit {

  error: string = '';
  success:boolean = false;
  incidencia: number = 0;
  incidencias: any[] = [];
  idUsuario: number = Number(localStorage.getItem('userId'))
  producto: number = 0;
  productos: any[] = [];
  loading = true; // Para mostrar mensaje de carga de datos en el HTML
  cantidad: number = 0;
  cestaArray: any[] = [];
  comentarioTecnico: string = '';

  constructor(public incidenciaService: IncidenciaService,public productoService: ProductoService) { }

  ngOnInit(): void {
    this.getIncidenciaLista(this.idUsuario);
    this.getProductosAlmacen(this.idUsuario)
  }

  getIncidenciaLista(idUsuario: number) {
    this.incidenciaService.getIncidenciasEnCurso(idUsuario).subscribe({
      next: (data: any[]) => {
        this.incidencias = data;
      },
      error: (err) => {
        this.error = err;
      }
    });
  }

  getProductosAlmacen(idUsuario:number) {
    this.loading= true;
    this.productoService.getProductosAlmacen(idUsuario).subscribe({
      next: (data: any[]) => {
        this.productos = data;
        this.loading = false; // Finaliza la carga
      },
      error: (err) => {
        this.error = err; // Asigna el mensaje de error
        this.loading = false; // Finaliza la carga
      }
    });
  }

  agregarACesta() {
    // Obtener el producto seleccionado del array de productos
    const productoSeleccionado = this.productos.find(producto => producto.idProducto.toString() === this.producto);

    // Verificar si el producto ya está en la cesta
    const productoExistente = this.cestaArray.find(item => item.idProducto === productoSeleccionado.idProducto);

    // Obtener la cantidad seleccionada
    const cantidadSeleccionada = Number(this.cantidad);

    // Si el producto ya está en la cesta, simplemente actualizamos su cantidad
    if (productoExistente) {
      productoExistente.cantidad += cantidadSeleccionada;
    } else {
      // Si no está en la cesta, lo añadimos junto con su cantidad
      this.cestaArray.push({
        idProducto: productoSeleccionado.idProducto,
        nombre: productoSeleccionado.nombre,
        cantidad: cantidadSeleccionada
      });
    }
  }

  eliminarProducto(idProducto: number){
    const index = this.cestaArray.findIndex(producto => producto.idProducto === idProducto);
    if (index !== -1) {
      this.cestaArray.splice(index, 1);
    }
  }

  vaciarCesta(){
    this.cestaArray=[]
  }

  obtenerIdYCantidad(): { idProducto: number, cantidad: number }[] {
    return this.cestaArray.map(producto => ({ idProducto: producto.idProducto, cantidad: producto.cantidad }));
  }

  finalizarIncidencia() {
    this.incidenciaService.finalizarIncidencia({
      idIncidencia: this.incidencia, 
      comentarioTecnico: this.comentarioTecnico,
      productosEnIncidencia: this.cestaArray 
    }).subscribe({
      next: response => {
        this.success = true;
      },
      error: (err) => {
        this.error = err;
      }
    });

  }


}

