package adv.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import adv.model.entities.Rol;
import adv.model.enums.RolNombre;


@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{
	
	Optional<Rol> findByRolNombre(RolNombre nombreRol);

}
