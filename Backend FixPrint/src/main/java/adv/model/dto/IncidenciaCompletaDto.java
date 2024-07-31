package adv.model.dto;

import java.util.Date;
import java.util.List;

import adv.model.enums.StatusIncidencia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class IncidenciaCompletaDto {

	private int idIncidencia;
	private int serialNumber;
	private String marca;
	private String modelo;
	private String descripcionCliente;
	private String comentarioTecnico;
	private StatusIncidencia estado;
	private Date fechaFin;
	private Date fechaInicio;
	private Date fechaAlta;
	private int idTecnico;
	private List<ProductoEnIncidenciaDto> productosEnIncidencia;
	
	
}
