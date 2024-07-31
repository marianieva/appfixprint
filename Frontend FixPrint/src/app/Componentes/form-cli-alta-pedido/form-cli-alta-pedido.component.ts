import { Component, OnInit } from '@angular/core';
import { UserService } from '../../Services/user.service';
import { ImpresoraService } from 'src/app/Services/impresora.service';
import { ProductoService } from 'src/app/Services/producto.service';
import { PedidosService } from 'src/app/Services/pedido.service';

@Component({
  selector: 'app-form-cli-alta-pedido',
  templateUrl: './form-cli-alta-pedido.component.html',
  styleUrls: ['./form-cli-alta-pedido.component.css']
})
export class FormCliAltaPedidoComponent implements OnInit {

  userRole: string = '';
  error: string = '';
  success: boolean = false;
  equipos: any[] = [];
  equipo: number = 0;
  productos: any[] = [];
  producto: number = 0;
  marca: string = '';
  cestaArray: any[] = [];
  cantidad: number = 0;
  idProducto: number = 0;
  rol: string | null = localStorage.getItem('roles')
  tipoPedido: string = ''
  userId: number = Number(localStorage.getItem('userId'));
  productosConsumibles: any[] = [];
  productoConsumible: number = 0;


  constructor(
    public userService: UserService,
    public impresoraService: ImpresoraService,
    public productoService: ProductoService,
    public pedidoService: PedidosService
  ) {}

  ngOnInit(): void {
    this.userRole = this.userService.getUserRole();
    if(this.rol !== null){
      switch(this.rol){
        case this.rol = 'ROL_TECNICO':
          this.getEquiposLista();
          break;
        case this.rol = 'ROL_CLIENTE':
          this.getEquiposCliente(this.userId);
          break;
      }
    }
  }

  obtenerIdYCantidad(): { idProducto: number, cantidad: number }[] {
    return this.cestaArray.map(producto => ({ idProducto: producto.idProducto, cantidad: producto.cantidad }));
  }
  

  altaPedidoTecnico(): void {

    this.obtenerIdYCantidad();
    
    if(this.rol !== null){
      switch(this.rol){
        case this.rol = 'ROL_TECNICO':
          this.tipoPedido = 'TECNICO';
          break;
        case this.rol = 'ROL_CLIENTE':
          this.tipoPedido = 'CLIENTE';
          break;
      }
    }

    this.pedidoService.crearPedido({
      tipoPedido: this.tipoPedido,
      idUsuario: Number(localStorage.getItem('userId')), 
      detallesDto: this.cestaArray
    }).subscribe({
      next: response => {
        this.success = true;
      },
      error: (err) => {
        this.error = err;
      }
    });
  }

  

  getEquiposLista() {
    this.impresoraService.getEquiposLista().subscribe({
      next: (data: any[]) => {
        this.equipos = data;
      },
      error: (err) => {
        this.error = err;
      }
    });
  }

  getEquiposCliente(userId: number){
    this.impresoraService.getEquiposCliente(userId).subscribe({
      next: (data: any[]) => {
        this.equipos = data;
      },
      error: (err) => {
        this.error = err;
      }
    });
  }

  productosByMarca(marca: string) {
    console.log('Buscando productos para la marca:', marca);
    this.productoService.getProductosPorMarcaTecnico(marca).subscribe({
      next: (data: any[]) => {
        this.productos = data;
        console.log('Productos recibidos:', this.productos);
      },
      error: (err) => {
        this.error = err;
        console.log('Error al buscar productos:', err);
      }
    });
  }

  productosByMarcaCliente(marca: string) {
    console.log('Buscando productos para la marca:', marca);
    this.productoService.getProductosPorMarcaCliente(marca).subscribe({
      next: (data: any[]) => {
        this.productosConsumibles = data;
        console.log('Productos recibidos:', this.productosConsumibles);
      },
      error: (err) => {
        this.error = err;
        console.log('Error al buscar productos:', err);
      }
    });
  }

  onEquipoChange() {
    console.log('Equipo seleccionado:', this.equipo);
    const equipoSeleccionado = this.equipos.find(e => e.serialNumber.toString() === this.equipo);
    console.log(equipoSeleccionado)
    if (equipoSeleccionado) {
      this.marca = equipoSeleccionado.marca;
      console.log('Equipo seleccionado:', equipoSeleccionado);
      console.log('Marca del equipo:', this.marca);
      this.productosByMarca(this.marca);
    } else {
      console.log('No se encontró el equipo seleccionado');
    }
  }

  onEquipoChangeCliente() {
    console.log('Equipo seleccionado:', this.equipo);
    const equipoSeleccionado = this.equipos.find(e => e.serialNumber.toString() === this.equipo);
    console.log(equipoSeleccionado)
    if (equipoSeleccionado) {
      this.marca = equipoSeleccionado.marca;
      console.log('Equipo seleccionado:', equipoSeleccionado);
      console.log('Marca del equipo:', this.marca);
      this.productosByMarcaCliente(this.marca);
    } else {
      console.log('No se encontró el equipo seleccionado');
    }
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
}