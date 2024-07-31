import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './Componentes/header/header.component';
import { FooterComponent } from './Componentes/footer/footer.component';
import { SliderComponent } from './Componentes/slider/slider.component';
import { CardComponent } from './Componentes/card/card.component';
import { ProfileCardComponent } from './Componentes/profile-card/profile-card.component';
import { FormCliAltaPedidoComponent } from './Componentes/form-cli-alta-pedido/form-cli-alta-pedido.component';
import { FormCliIncidenciaComponent } from './Componentes/form-cli-incidencia/form-cli-incidencia.component';
import { FormModificarUsuarioComponent } from './Componentes/form-modificar-usuario/form-modificar-usuario.component';
import { FormModificarPasswordComponent } from './Componentes/form-modificar-password/form-modificar-password.component';
import { FormTecIncidenciaComponent } from './Componentes/form-tec-incidencia/form-tec-incidencia.component';
import { FormAdminIncidenciaComponent } from './Componentes/form-admin-incidencia/form-admin-incidencia.component';
import { FormAdminAltaUsuarioComponent } from './Componentes/form-admin-alta-usuario/form-admin-alta-usuario.component';
import { FormAdminImpresoraComponent } from './Componentes/form-admin-impresora/form-admin-impresora.component';
import { FormAdminModificarUsuarioComponent } from './Componentes/form-admin-modificar-usuario/form-admin-modificar-usuario.component';

import { HomeComponent } from './Bodies/home/home.component';
import { UsuarioComponent } from './Bodies/usuario/usuario.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { TableCliEquiposComponent } from './Componentes/table-cli-equipos/table-cli-equipos.component';
import { PedidosComponent } from './Bodies/pedidos/pedidos.component';
import { TableCliPedidosComponent } from './Componentes/table-cli-pedidos/table-cli-pedidos.component';
import { IncidenciasComponent } from './Bodies/incidencias/incidencias.component';
import { TableCliIncidenciasComponent } from './Componentes/table-cli-incidencias/table-cli-incidencias.component';
import { TableTecIncidenciasFinComponent } from './Componentes/table-tec-incidencias-fin/table-tec-incidencias-fin.component';
import { TableTecAlmacenComponent } from './Componentes/table-tec-almacen/table-tec-almacen.component';
import { TableTecIncidenciasPendComponent } from './Componentes/table-tec-incidencias-pend/table-tec-incidencias-pend.component';
import { LoginComponent } from './Bodies/login/login.component';
import { FormLoginComponent } from './Componentes/form-login/form-login.component';
import { ErrorComponent } from './Bodies/error/error.component';
import { ModalComponent } from './Componentes/modal/modal.component';
import { TableAdminIncidenciasFinComponent } from './Componentes/table-admin-incidencias-fin/table-admin-incidencias-fin.component';
import { TableAdminPedidosComponent } from './Componentes/table-admin-pedidos/table-admin-pedidos.component';
import { TableAdminUsuariosComponent } from './Componentes/table-admin-usuarios/table-admin-usuarios.component';
import { AuthInterceptor } from './Interceptor/auth.interceptor';
import { FormsModule } from '@angular/forms';
import { AuthService } from './Services/auth.service';


@NgModule({
  declarations: [	
    AppComponent,
      HeaderComponent,
      FooterComponent,
      SliderComponent,
      CardComponent,
      ProfileCardComponent,
      FormCliAltaPedidoComponent,
      FormCliIncidenciaComponent,
      FormModificarUsuarioComponent,
      FormModificarPasswordComponent,
      FormTecIncidenciaComponent,
      FormAdminIncidenciaComponent,
      FormAdminAltaUsuarioComponent,
      FormAdminImpresoraComponent,
      FormAdminModificarUsuarioComponent,
      HomeComponent,
      UsuarioComponent,
      TableCliEquiposComponent,
      PedidosComponent,
      TableCliPedidosComponent,
      IncidenciasComponent,
      TableCliIncidenciasComponent,
      TableTecIncidenciasFinComponent,
      TableTecAlmacenComponent,
      TableTecIncidenciasPendComponent,
      LoginComponent,
      FormLoginComponent,
      ErrorComponent,
      ModalComponent,
      TableAdminIncidenciasFinComponent,
      TableAdminPedidosComponent,
      TableAdminUsuariosComponent
   ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [
    AuthService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
