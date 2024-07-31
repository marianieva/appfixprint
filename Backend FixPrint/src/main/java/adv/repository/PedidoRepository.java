package adv.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import adv.model.entities.Pedido;
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{
	

}
