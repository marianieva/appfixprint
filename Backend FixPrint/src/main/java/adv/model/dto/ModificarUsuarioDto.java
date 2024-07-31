package adv.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ModificarUsuarioDto {

	private int idUsuario;

	private String username;

	private String apellidos;

	private String direccion;

	private String nombre;

	
	
	

}