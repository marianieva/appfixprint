package adv.model.entities;

import java.io.Serializable;
import java.util.Objects;

import adv.model.enums.RolNombre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 


/**
 * The persistent class for the roles database table.
 * 
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="roles")
@NamedQuery(name="Rol.findAll", query="SELECT r FROM Rol r")
public class Rol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_rol")
	private int idRol;

	@Column(name="nombre_rol")
	@Enumerated(EnumType.STRING)
	private RolNombre rolNombre;

	public Rol(int idRol) {
		this.idRol = idRol;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Rol))
			return false;
		Rol other = (Rol) obj;
		return idRol == other.idRol && rolNombre == other.rolNombre;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idRol, rolNombre);
	}
	
	
	
	
	
}