


----------- CONSTRUCCION BBDD CON DATOS ---------------




-------------- CONSTRUCCION DE TABLAS ---------------


create database adv_v3;
use adv_v3;

CREATE TABLE `zonas` (
	id_zona int NOT NULL AUTO_INCREMENT,
	nombre_zona varchar(20) NOT NULL,
	PRIMARY KEY(id_zona)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `roles` (
	id_rol int NOT NULL AUTO_INCREMENT,
	nombre_rol varchar(50) NOT NULL,
	PRIMARY KEY (id_rol)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `usuarios` (
	id_usuario int not null AUTO_INCREMENT,
	username varchar(50) NOT NULL,
	password varchar(100) NOT NULL,
	nombre varchar(100) NOT NULL,
	apellidos varchar(100),
	direccion varchar(100),
	id_zona int NOT NULL,
	PRIMARY KEY (id_usuario),
	FOREIGN KEY (id_zona) REFERENCES `zonas` (id_zona)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `impresoras` (
	serial_number int NOT NULL AUTO_INCREMENT,
	marca varchar(45) NOT NULL,
	modelo varchar(100) not null,
	id_usuario int not null,
	PRIMARY KEY (serial_number),
	FOREIGN KEY (id_usuario) REFERENCES `usuarios` (id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `incidencias` (
	id_incidencia int NOT NULL AUTO_INCREMENT,
	serial_number int NOT NULL,
	id_tecnico int NOT NULL,
	descripcion_cliente varchar(250) NOT NULL,
	comentario_tecnico varchar(250),
	estado ENUM('PENDIENTE', 'ENCURSO', 'TERMINADA', 'CANCELADA') NOT NULL,
    fecha_inicio datetime,
    fecha_fin datetime,
    fecha_alta datetime,
	PRIMARY KEY (id_incidencia),
	FOREIGN KEY (serial_number) REFERENCES `impresoras` (serial_number),
	FOREIGN KEY (id_tecnico) REFERENCES `usuarios` (id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `productos` (
	id_producto int NOT NULL AUTO_INCREMENT,
	nombre varchar(100) NOT NULL,
	tipo_producto varchar(10),
	descripcion varchar(2000),
	stock int,
	marca varchar(20),
	PRIMARY KEY (id_producto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `pedidos` (
  id_pedido int NOT NULL AUTO_INCREMENT,
  id_usuario int NOT NULL,
  fecha_pedido date,
  tipo_pedido varchar(10),
  estado varchar(10) NOT NULL,
  PRIMARY KEY (id_pedido),
  FOREIGN KEY (id_usuario) REFERENCES `usuarios` (id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `detalle_pedido` (
  id_detalle_pedido int NOT NULL AUTO_INCREMENT,
  id_pedido int not null,
  id_producto int not null,
  cantidad int,
  PRIMARY KEY (id_detalle_pedido),
  FOREIGN KEY (id_pedido) REFERENCES `pedidos` (id_pedido),
  FOREIGN KEY (id_producto) REFERENCES `productos` (id_producto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `productos_en_incidencia` (
  id_producto_incidencia int NOT NULL AUTO_INCREMENT,
  id_incidencia int NOT NULL,
  id_producto int NOT NULL,
  PRIMARY KEY (id_producto_incidencia),
  FOREIGN KEY (id_incidencia) REFERENCES `incidencias` (id_incidencia),
  FOREIGN KEY (id_producto) REFERENCES `productos` (id_producto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `almacen_usuario` (
  id_almacen_usuario int NOT NULL AUTO_INCREMENT,
  id_usuario int NOT NULL,
  id_producto int,
  stock int,
  PRIMARY KEY (id_almacen_usuario),
  FOREIGN KEY (id_usuario) REFERENCES `usuarios` (id_usuario),
  FOREIGN KEY (id_producto) REFERENCES `productos` (id_producto)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `usuario_rol` (
  id_usuario int NOT NULL,
  id_rol int NOT NULL,
  FOREIGN KEY (id_usuario) REFERENCES `usuarios` (id_usuario),
  FOREIGN KEY (id_rol) REFERENCES `roles` (id_rol)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


----------- INSERTAR DATOS ---------------

use adv_v3;
-- Insertar datos en la tabla zonas
INSERT INTO zonas (nombre_zona) VALUES ('Madrid Centro'), ('Madrid Norte'), ('Madrid Sur');

-- Insertar datos en la tabla roles
INSERT INTO roles (nombre_rol) VALUES ('ROL_INVITADO'), ('ROL_CLIENTE'), ('ROL_TECNICO'),('ROL_ADMIN' );

-- Insertar datos en la tabla usuarios
INSERT INTO usuarios (id_usuario, username, password, nombre, apellidos, direccion, id_zona) VALUES
(1, 'admin', '$2a$12$RMs7WaR.21YhMvjJv6ikletxNhOrHfoP1kVp/e6iRV2FLquVNsRMO', 'admin', 'admin', 'Calle Principal 123', 1),
(2, 'tecnico1', '$2a$12$EcPVMDdWpthLYRZFgLJ3rOBb0hXzv1mTKb3iYpgA5HZctLJtINzeu', 'Técnico1', 'Centro', 'Calle Secundaria 456', 1),
(3, 'tecnico2', '$2a$12$EcPVMDdWpthLYRZFgLJ3rOBb0hXzv1mTKb3iYpgA5HZctLJtINzeu', 'Técnico2', 'Norte', 'Calle Secundaria 456', 2),
(4, 'cliente1', '$2a$12$8uQDIrT9W6GJgVj8MguYY.X2obFRPZVRJvGXb/WRUVTqIiF1aFQsy', 'Cliente1', 'Centro', 'Avenida Central 789', 1),
(5, 'cliente2', '$2a$12$M5cFgiEswxW5yVaAwEEg8uwjOHIgB9gTW5raw96M3O8GkmQCjAo5K', 'Cliente2', 'Norte', 'Avenida Central 789', 2);


-- Insertar datos en la tabla impresoras
INSERT INTO impresoras (serial_number, marca, modelo, id_usuario) VALUES
(123456, 'HP', 'Deskjet', 4),
(234567, 'Epson', 'Laserjet', 4),
(345678, 'Canon', 'Pixma', 5),
(345325, 'Konica Minolta','C308', 5);

-- Insertar datos en la tabla incidencias
INSERT INTO incidencias (serial_number, id_tecnico, descripcion_cliente, comentario_tecnico, estado, fecha_inicio, fecha_fin, fecha_alta) VALUES
(123456, 2, 'Impresora no imprime correctamente', NULL, 'PENDIENTE', NULL, NULL, '2024-05-15'),
(234567, 2, 'Impresora se queda atascada al imprimir', NULL, 'PENDIENTE', NULL, NULL, '2024-05-14'),
(345678, 2, 'Impresora no enciende', NULL, 'TERMINADA', '2024-05-13', '2024-05-15', '2024-05-13');

-- Insertar datos en la tabla productos
INSERT INTO productos (id_producto, nombre, tipo_producto, descripcion, stock, marca) VALUES
(1,'Toner Negro', 'CONSUMIBLE', 'Toner Negro para HP Deskjet', 25, 'HP'),
(2,'Toner Magenta', 'CONSUMIBLE', 'Toner Magenta para HP Deskjet', 25, 'HP'),
(3,'Toner Cyan', 'CONSUMIBLE', 'Toner Cyan para HP Deskjet', 25, 'HP'),
(4,'Toner Amarillo', 'CONSUMIBLE', 'Toner Amarillo para HP Deskjet', 25, 'HP'),
(5,'Toner Negro', 'CONSUMIBLE', 'Toner Negro para Epson Laserjet', 25, 'Epson'),
(6,'Toner Magenta', 'CONSUMIBLE', 'Toner Magenta para Epson Laserjet', 25, 'Epson'),
(7,'Toner Cyan', 'CONSUMIBLE', 'Toner Cyan para Epson Laserjet', 25, 'Epson'),
(8,'Toner Amarillo', 'CONSUMIBLE', 'Toner Amarillo para Epson Laserjet', 25, 'Epson'),
(9,'Toner Negro', 'CONSUMIBLE', 'Toner Negro para Canon Pixma', 25, 'Canon'),
(10,'Toner Magenta', 'CONSUMIBLE', 'Toner Magenta  para Canon Pixma', 25, 'Canon'),
(11,'Toner Cyan', 'CONSUMIBLE', 'Toner Cyan  para Canon Pixma', 25, 'Canon'),
(12,'Toner Amarillo', 'CONSUMIBLE', 'Toner Amarillo para Canon Pixma', 25, 'Canon'),
(13,'FUSOR HP Deskjet', 'REPUESTO', 'Unidad de fusion para HP Deskjet' , 10, 'HP'),
(14,'DRUM HP Deskjet', 'REPUESTO', 'Unidad de imagen para HP Deskjet' , 10, 'HP'),
(15,'FUSOR Epson Laserjet', 'REPUESTO', 'Unidad de fusion para Epson Laserjet' , 10, 'Epson'),
(16,'DRUM Epson Laserjet', 'REPUESTO', 'Unidad de imagen para Epson Laserjet' , 10, 'Epson'),
(17,'FUSOR Konica Minolta', 'REPUESTO', 'Unidad de fusion para Konica Minolta C308' , 10, 'Konica Minolta'),
(18,'DRUM Konica Minolta', 'REPUESTO', 'Unidad de imagen para Konica Minolta C308' , 10, 'Konica Minolta');

INSERT INTO usuario_rol (id_usuario, id_rol) VALUES
(1,4),
(2,3),
(3,3),
(4,2),
(5,2);






