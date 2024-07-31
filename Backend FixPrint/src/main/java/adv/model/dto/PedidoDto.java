package adv.model.dto;

import java.util.Date;
import java.util.List;

import adv.model.entities.DetallePedido;
import adv.model.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PedidoDto {
	
	private int idPedido;
	private String tipoPedido;
	private int idUsuario;
	private StatusPedido estado;
	private Date fechaPedido;
	private List<DetallePedidoDto> detallesDto;
	private List<DetallePedido> detalles;
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof PedidoDto))
			return false;
		PedidoDto other = (PedidoDto) obj;
		if (idPedido == other.idPedido)
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idPedido;
		return result;
	}

}
