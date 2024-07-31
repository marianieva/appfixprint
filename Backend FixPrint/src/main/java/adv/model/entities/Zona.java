package adv.model.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the zonas database table.
 * 
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="zonas")
@NamedQuery(name="Zona.findAll", query="SELECT z FROM Zona z")
public class Zona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_zona")
	private int idZona;

	@Column(name="nombre_zona")
	private String nombreZona;

	public Zona(int idZona) {
		this.idZona = idZona;
	}

	public int getIdZona() {
		return this.idZona;
	}

	public void setIdZona(int idZona) {
		this.idZona = idZona;
	}

	public String getNombreZona() {
		return this.nombreZona;
	}

	public void setNombreZona(String nombreZona) {
		this.nombreZona = nombreZona;
	}

}