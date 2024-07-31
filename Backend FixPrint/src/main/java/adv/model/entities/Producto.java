package adv.model.entities;

import java.io.Serializable;

import adv.model.enums.TipoProducto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the productos database table.
 * 
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "productos")
@NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p")
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_producto")
	private int idProducto;

	private String nombre;

	private String descripcion;

	private String marca;

	private int stock;

	@Column(name = "tipo_producto")
	@Enumerated(value = EnumType.STRING)
	private TipoProducto tipoProducto;
	

}