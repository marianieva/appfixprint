package adv.service;

import java.util.List;
import java.util.Optional;

import adv.model.entities.Pedido;
import adv.model.entities.Producto;
import adv.model.entities.Usuario;

public interface UsuarioService {

	// USUARIOS

	/**
	 * Método para obtener todos los usuarios.
	 *
	 * @return Una lista de todos los usuarios.
	 */
	List<Usuario> listaUsuarios();

	/**
	 * Método para dar de alta un nuevo usuario.
	 *
	 * @param usuario El usuario a dar de alta.
	 * @return El usuario dado de alta.
	 */
	Usuario altaUsuario(Usuario usuario);

	/**
	 * Método para encontrar un usuario por su identificador.
	 *
	 * @param idUsuario El identificador del usuario a buscar.
	 * @return El usuario encontrado o nulo si no existe.
	 */
	Usuario findOne(int idUsuario);

	/**
	 * Método para actualizar la información de un usuario.
	 *
	 * @param usuario El usuario con la información actualizada.
	 * @return El usuario actualizado o nulo si no se pudo actualizar.
	 */
	Usuario updateUsuario(Usuario usuario);
	
	/**
	 * Método para obtener el rol de un usuario.
	 *
	 * @param usuario El usuario del que se desea obtener el rol.
	 * @return El identificador del rol del usuario.
	 */
	int dimeElRol(Usuario usuario);

	// TECNICOS

	/**
	 * Método para obtener todos los técnicos.
	 *
	 * @return Una lista de todos los técnicos.
	 */
	List<Usuario> listaTecnicos();

	/**
	 * Método para dar de alta un nuevo técnico.
	 *
	 * @param tecnico El técnico a dar de alta.
	 * @return El técnico dado de alta.
	 */
	Usuario altaTecnico(Usuario tecnico);

	/**
	 * Método para encontrar un técnico por su identificador.
	 *
	 * @param idTecnico El identificador del técnico a buscar.
	 * @return El técnico encontrado o nulo si no existe.
	 */
	Usuario findOneTecnico(int idTecnico);

	/**
	 * Método para actualizar la información de un técnico.
	 *
	 * @param tecnico El técnico con la información actualizada.
	 * @return El técnico actualizado o nulo si no se pudo actualizar.
	 */
	Usuario updateTecnico(Usuario tecnico);

	/**
	 * Método para encontrar un técnico por su zona.
	 *
	 * @param zona El identificador de la zona del técnico a buscar.
	 * @return El técnico encontrado o nulo si no existe.
	 */
	Usuario tecnicoPorZona(int zona);
	

	// CLIENTES

	/**
	 * Método para obtener todos los clientes.
	 *
	 * @return Una lista de todos los clientes.
	 */
	List<Usuario> listaClientes();

	/**
	 * Método para dar de alta un nuevo cliente.
	 *
	 * @param cliente El cliente a dar de alta.
	 * @return El cliente dado de alta.
	 */
	Usuario altaCliente(Usuario cliente);

	/**
	 * Método para encontrar un cliente por su identificador.
	 *
	 * @param idCliente El identificador del cliente a buscar.
	 * @return El cliente encontrado o nulo si no existe.
	 */
	Usuario findOneCliente(int idCliente);

	/**
	 * Método para actualizar la información de un cliente.
	 *
	 * @param cliente El cliente con la información actualizada.
	 * @return El cliente actualizado o nulo si no se pudo actualizar.
	 */
	Usuario updateCliente(Usuario cliente);
	
	
	// SPRING BOOT SECURITY
	
	Optional<Usuario> getByUsername(String username);
	boolean existsByUsername(String username);
	
	// STOCK
	
	void actualizarStockUsuario(Pedido pedido);
	
	/**
	 * Método para obtener todos los productos asociados a un usuario.
	 *
	 * @param idUsuario El identificador del usuario.
	 * @return Una lista de productos asociados al usuario.
	 */
	List<Producto> productosPorUsuario(int idUsuario);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
