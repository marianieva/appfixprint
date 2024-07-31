package adv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import adv.model.entities.ProductoEnIncidencia;

@Repository
public interface ProductoEnIncidenciaRepository extends JpaRepository<ProductoEnIncidencia, Integer>{

}
