package adv.service;

import java.util.List;
import java.util.Optional;

import adv.model.entities.Rol;
import adv.model.entities.Usuario;
import adv.model.enums.RolNombre;

public interface RolService {
	
	Rol dimeElRol(Usuario usuario);
	
	Optional<Rol> getByRolNombre(RolNombre rolNombre);
	
	List<Rol> findAllRole();
	

}
