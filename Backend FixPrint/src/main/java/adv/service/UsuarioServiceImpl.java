package adv.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import adv.model.entities.AlmacenUsuario;
import adv.model.entities.DetallePedido;
import adv.model.entities.Pedido;
import adv.model.entities.Producto;
import adv.model.entities.Rol;
import adv.model.entities.Usuario;
import adv.model.enums.RolNombre;
import adv.repository.AlmacenUsuarioRepository;
import adv.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RolService rolService;
	
	@Autowired
	private AlmacenUsuarioRepository almacenUsuarioRepository;

	// USUARIOS

	@Override
	public List<Usuario> listaUsuarios() {
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario altaUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Override
	public Usuario findOne(int idUsuario) {
		return usuarioRepository.findById(idUsuario).orElse(null);
	}

	@Override
	public Usuario updateUsuario(Usuario usuario) {
		
			return usuarioRepository.save(usuario);
		
	}

	@Override
	public int dimeElRol(Usuario usuario) {

		for (Rol rol : usuario.getRoles()) {

			switch (rol.getRolNombre()) {

			case ROL_INVITADO:
				return 1;
			case ROL_CLIENTE:
				return 2;
			case ROL_TECNICO:
				return 3;
			case ROL_ADMIN:
				return 4;
			}
		}
		return 1;
	}
	
	
	@Override
	public void actualizarStockUsuario(Pedido pedido) {
		Usuario usuario = pedido.getUsuario();
		List<DetallePedido> detalles = pedido.getDetallePedidos();
		System.out.println(detalles);
		for(DetallePedido detalle: detalles) {
			Producto producto = detalle.getProducto();
			int cantidad = detalle.getCantidad();
			
			AlmacenUsuario almacenUsuario = almacenUsuarioRepository.findByUsuarioAndProducto(usuario, producto);
			
			if (almacenUsuario != null) {
	            // Si ya existe, actualizar el stock
				almacenUsuario.setStock(almacenUsuario.getStock() + cantidad);
	        } else {
	            // Si no existe, crear una nueva entrada
	        	almacenUsuario = new AlmacenUsuario();
	        	almacenUsuario.setUsuario(usuario);
	        	almacenUsuario.setProducto(producto);
	            almacenUsuario.setStock(cantidad);
	        }

	        almacenUsuarioRepository.save(almacenUsuario);
	    }
	}
	
	@Override
	public List<Producto> productosPorUsuario(int idUsuario) {
		
		List<Producto> productosEnAlmacen = new ArrayList<>();
		
		for (AlmacenUsuario almacen: almacenUsuarioRepository.findByUsuario(findOne(idUsuario))) {
			
			Producto producto = new Producto();
			producto.setIdProducto(almacen.getProducto().getIdProducto());
			producto.setNombre(almacen.getProducto().getNombre());
			producto.setDescripcion(almacen.getProducto().getDescripcion());
			producto.setTipoProducto(almacen.getProducto().getTipoProducto());
			producto.setMarca(almacen.getProducto().getMarca());
			producto.setStock(almacen.getStock());
			productosEnAlmacen.add(producto);
		}
		
		return productosEnAlmacen;
	}
	
	
	

	// TECNICOS
	@Override
	public List<Usuario> listaTecnicos() {

		List<Usuario> usuarios = this.listaUsuarios();
		List<Usuario> usuariosTecnico = new ArrayList<>();

		for (Usuario usuario : usuarios) {
			if (dimeElRol(usuario) == 3) {
				usuariosTecnico.add(usuario);
			}
		}
		return usuariosTecnico;
	}

	@Override
	public Usuario altaTecnico(Usuario tecnico) {
		
		 tecnico.addRol(rolService.getByRolNombre(RolNombre.ROL_TECNICO).get()); 
		 return usuarioRepository.save(tecnico);
	}

	@Override
	public Usuario findOneTecnico(int idTecnico) {
		return usuarioRepository.findById(idTecnico).orElse(null);
	}

	@Override
	public Usuario updateTecnico(Usuario tecnico) {
		try {
			return usuarioRepository.save(tecnico);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Usuario tecnicoPorZona(int zona) {
		List<Usuario> listaTecnicos = this.listaTecnicos();
		for (Usuario usuario : listaTecnicos) {
			if (usuario.getZona().getIdZona() == zona) {
				return usuario;
			}
		}
		return null;
	}

	// CLIENTES

	@Override
	public List<Usuario> listaClientes() {

		List<Usuario> usuarios = this.listaUsuarios();
		List<Usuario> usuariosCliente = new ArrayList<>();

		for (Usuario usuario : usuarios) {
			if (dimeElRol(usuario) == 2) {
				usuariosCliente.add(usuario);
			}
		}
		return usuariosCliente;
	}

	@Override
	public Usuario altaCliente(Usuario cliente) {

		cliente.addRol(rolService.getByRolNombre(RolNombre.ROL_CLIENTE).get()); 
		 return usuarioRepository.save(cliente);
		
	}

	@Override
	public Usuario findOneCliente(int idCliente) {
		return usuarioRepository.findById(idCliente).orElse(null);
	}

	@Override
	public Usuario updateCliente(Usuario cliente) {
		try {
			return usuarioRepository.save(cliente);
		} catch (Exception e) {
			return null;
		}
	}

	// METODOS SPRING BOOT SECURITY

	@Override
	public Optional<Usuario> getByUsername(String username) {
		return usuarioRepository.findByUsername(username);
	}

	@Override
	public boolean existsByUsername(String username) {
		return usuarioRepository.existsByUsername(username);
	}

	


}
