package adv.model.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductoDto {
	
	private int idProducto;
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ProductoDto))
			return false;
		ProductoDto other = (ProductoDto) obj;
		if (idProducto == other.idProducto)
			return false;
		return true;
	}
	
	
	
	

}
