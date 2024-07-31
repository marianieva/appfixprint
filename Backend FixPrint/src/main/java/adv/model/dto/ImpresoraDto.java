package adv.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ImpresoraDto {
	
	private int serialNumber;
	private String marca;
	private String modelo;
	private int usuario;
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ImpresoraDto))
			return false;
		ImpresoraDto other = (ImpresoraDto) obj;
		if (serialNumber == other.serialNumber)
			return false;
		return true;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + serialNumber;
		return result;
	}
	
}
