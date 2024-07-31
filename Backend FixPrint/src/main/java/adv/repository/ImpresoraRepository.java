package adv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import adv.model.entities.Impresora;

@Repository
public interface ImpresoraRepository extends JpaRepository<Impresora, Integer>{
	
	// @Query("select i from Impresora i where i.usuario.idUsuario = ?1")
	// List<Impresora> impresorasPorCliente(int idCliente);

}
