package adv.model.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the impresoras database table.
 * 
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="impresoras")
@NamedQuery(name="Impresora.findAll", query="SELECT i FROM Impresora i")
public class Impresora implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="serial_number")
	private int serialNumber;

	private String marca;

	private String modelo;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_usuario")
	private Usuario usuario; 

}