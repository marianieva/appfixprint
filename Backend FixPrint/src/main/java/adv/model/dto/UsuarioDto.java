package adv.model.dto;

import adv.model.entities.Rol;
import adv.model.entities.Zona;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UsuarioDto {

	private int idUsuario;

	private String username;

	private String apellidos;

	private String direccion;

	private String nombre;

	private String password;
	
	private Zona zona;
	
	private Rol rol;
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof UsuarioDto))
			return false;
		UsuarioDto other = (UsuarioDto) obj;
		if (idUsuario == other.idUsuario)
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idUsuario;
		return result;
	}
	

}
