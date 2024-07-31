package adv.model.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class IncidenciaDto {
	
	private int idIncidencia;
	private int serialNumber;
	private String descripcionCliente;
	private List<Integer> idsProductos;
	private Date fechaInicio;
	private Date fechaFin;
	private Date fechaAlta;
	private String estado;
	private String marca;
	private String modelo;
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof IncidenciaDto))
			return false;
		IncidenciaDto other = (IncidenciaDto) obj;
		if (idIncidencia == other.idIncidencia)
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idIncidencia;
		return result;
	}
	
	

}
