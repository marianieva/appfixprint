package adv.service;

import java.util.List;

import adv.model.dto.PedidoDto;
import adv.model.entities.Pedido;

public interface PedidoService {
	
	/**
	 * Método para obtener todos los pedidos.
	 *
	 * @return Una lista de todos los pedidos.
	 */
	List<Pedido> listaPedidos();
	
	/**
	 * Método para obtener todos los pedidos asociados a un usuario.
	 *
	 * @param idUsuario El identificador del usuario.
	 * @return Una lista de pedidos asociados al usuario.
	 */
	List<Pedido> listaPedidosPorUsuario(int idUsuario);
	
	/**
	 * Método para encontrar un pedido por su identificador.
	 *
	 * @param idPedido El identificador del pedido a buscar.
	 * @return El pedido encontrado o nulo si no existe.
	 */
	Pedido findOne (int idIncidencia);
	
	/**
	 * Método para actualizar un pedido existente.
	 *
	 * @param pedido El pedido a actualizar.
	 * @return El pedido actualizado o nulo si ocurre un error.
	 */
	Pedido updateOne(Pedido pedido);
	
	/**
	 * Método para crear un nuevo pedido.
	 *
	 * @param pedido El pedido a crear.
	 * @return El pedido creado.
	 */
	Pedido createPedido(Pedido pedido);
	
	/**
	 * Método para dar de alta un nuevo pedido.
	 *
	 * @param PedidoDto Es un DTO del pedido a dar de alta.
	 * @return El pedido dado de alta.
	 */
	Pedido altaPedido(PedidoDto pedidoDto);
	
	/**
	 * Método para confirmar un pedido, si el stock es superior al numero de productos que se solicitan devolvera true y se actualizará el stock en la tabla productos,
	 * Si el stock es inferior al numero de productos solicitados devolvera false.
	 *
	 * @param idPedido El identificador del pedido a confirmar.
	 * @return true si se confirmó correctamente, false de lo contrario.
	 */
	boolean confirmarPedido(int idPedido);
	
	List<Pedido> listaPedidosRecambios();
    List<Pedido> listaPedidosConsumibles();
    List<PedidoDto> listaPedidosPorUsuarioDto(int idUsuario);

}
