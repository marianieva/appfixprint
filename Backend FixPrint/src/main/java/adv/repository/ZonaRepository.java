package adv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import adv.model.entities.Zona;

@Repository
public interface ZonaRepository extends JpaRepository<Zona, Integer>{

}
