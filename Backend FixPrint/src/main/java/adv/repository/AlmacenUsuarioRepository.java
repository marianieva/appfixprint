package adv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import adv.model.entities.AlmacenUsuario;
import adv.model.entities.Producto;
import adv.model.entities.Usuario;
@Repository
public interface AlmacenUsuarioRepository extends JpaRepository<AlmacenUsuario, Integer>{
	
	AlmacenUsuario findByUsuarioAndProducto(Usuario usuario, Producto producto);
	
	List<AlmacenUsuario> findByUsuario(Usuario usuario);

}
