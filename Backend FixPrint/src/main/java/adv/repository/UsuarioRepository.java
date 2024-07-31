package adv.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import adv.model.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	
	Optional<Usuario> findByUsername(String username);
	boolean existsByUsername(String username);
	int findIdByUsername(String username);
	
	// @Query("select u from Usuario u where u.rol.idRol = 2")
	// public List<Usuario> todosTecnicos();
	
	// @Query("select u from Usuario u where u.rol.idRol = 3")
	// public List<Usuario> todosClientes();
	
	// @Query("select u from Usuario u where u.zona.idZona = ?1 and u.rol.idRol = 2")
	// public Usuario tecnicoPorZona(int idZona);
	
	

	
}
