import { Component, OnInit, AfterViewChecked } from '@angular/core';
import { UserService } from '../../Services/user.service';

@Component({
  selector: 'app-table-admin-usuarios',
  templateUrl: './table-admin-usuarios.component.html',
  styleUrls: ['./table-admin-usuarios.component.css']
})
export class TableAdminUsuariosComponent implements OnInit{

  usuarios: any[] = []; // Para cargar el array con el JSON que devuelva la petición GET
  loading = true; // Para mostrar mensaje de carga de datos en el HTML
  error = ''; // Para manejar los errores en la carga de datos de la petición GET
  currentView: 'all' | 'tecnicos' | 'clientes' = 'all';
  finalized: any;
  showModal: boolean = false;
  modalType: 'new-user' | 'update-user' | 'new-print'= 'new-user';
  nombre: string = '';
  apellidos:string = '';
  username: string = '';
  password: string = '';
  direccion: string = '';

  constructor(public service: UserService) { }
  
    ngOnInit(): void {
      this.getUsuarios();
    }
  
    getUsuarios() {
      this.loading= true;
      this.service.getUsuariosAdmin().subscribe({
        next: (data: any[]) => {
          this.usuarios = data;
          this.loading = false; // Finaliza la carga
        },
        error: (err) => {
          this.error = err; // Asigna el mensaje de error
          this.loading = false; // Finaliza la carga
        }
      });
    }
  
    getTecnicos(){
      this.loading= true;
      this.service.getTecnicosAdmin().subscribe({
        next: (data: any[]) => {
          this.usuarios = data;
          this.loading = false; // Finaliza la carga
        },
        error: (err) => {
          this.error = err; // Asigna el mensaje de error
          this.loading = false; // Finaliza la carga
        }
      });
    }
    getClientes(){
      this.loading= true;
      this.service.getClientesAdmin().subscribe({
        next: (data: any[]) => {
          this.usuarios = data;
          this.loading = false; // Finaliza la carga
        },
        error: (err) => {
          this.error = err; // Asigna el mensaje de error
          this.loading = false; // Finaliza la carga
        }
      });
    }
  
    changeView(view: 'all' | 'tecnicos' | 'clientes') {
      this.currentView = view;
      if (view === 'all') {
        this.getUsuarios();
      } else if (view === 'tecnicos') {
        this.getTecnicos();
      } else if (view === 'clientes') {
        this.getClientes();
      }
    }

    getRoleLabel(rolNombre: string): string {
      switch (rolNombre) {
        case 'ROL_CLIENTE':
          return 'Cliente';
        case 'ROL_TECNICO':
          return 'Tecnico';
        case 'ROL_ADMIN':
          return 'Administrador';
        case 'ROL_INVITADO':
          return 'Invitado';
        default:
          return rolNombre;
      }
    }

    showNewUser(){
      this.modalType = 'new-user';
      this.showModal = true;
    }
    showUpdateUser(idUsuario: number){
      this.modalType = 'update-user';
      this.showModal = true;
      this.getUser(idUsuario);
      localStorage.setItem('idUserUpdate', idUsuario.toString())
    }
    showNewPrint(){
      this.modalType = 'new-print';
      this.showModal = true;
    }

    closeModal() {
      this.showModal = false;
    }

    getUser(idUsuario: number): void {
      this.service.getUser(idUsuario).subscribe({
        next: (userData: any) => {
          // Asigna los datos del usuario a las propiedades del componente
          this.nombre = userData.nombre;
          this.apellidos = userData.apellidos;
          this.username = userData.username;
          this.direccion = userData.direccion;
        },
        error: (err) => {
          console.error(err); // Maneja el error
        }
  
      });
    }
}
