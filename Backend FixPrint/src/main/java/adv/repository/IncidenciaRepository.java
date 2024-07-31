package adv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import adv.model.entities.Incidencia;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer>{
	
	
	@Query("SELECT i from Incidencia i where i.serialNumber.usuario.idUsuario = ?1")
	List<Incidencia> incidenciasPorUsuario(int idUsuario);
	
	@Query("SELECT i from Incidencia i where i.serialNumber.serialNumber = ?1")
	List<Incidencia> incidenciasPorImpresora(int serialNumber);
	
	@Query("SELECT i from Incidencia i where i.tecnico.idUsuario = ?1")
	List<Incidencia> incidenciasPorTecnico(int idTecnico);

}
