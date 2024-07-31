package adv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import adv.model.entities.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{
	
	@Query("select p from Producto p where p.marca = ?1")
	List<Producto> productosPorMarca(String marca);
	
	@Query("select p from Producto p where p.tipoProducto = 'CONSUMIBLE'")
	List<Producto> productosPorConsumible();
	
	@Query("select p from Producto p where p.marca = ?1 and p.tipoProducto = 'CONSUMIBLE'")
	List<Producto> productosConsumibleYMarca(String marca);
	
	@Query("select p from Producto p where p.tipoProducto = 'REPUESTO'")
	List<Producto> productosPorRepuesto();
	
	@Query("select p from Producto p where p.marca = ?1 and p.tipoProducto = 'REPUESTO'")
	List<Producto> productosPorRepuestoYMarca(String marca);
	

}
