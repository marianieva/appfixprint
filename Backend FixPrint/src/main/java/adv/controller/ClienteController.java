package adv.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import adv.model.dto.ModificarUsuarioDto;
import adv.model.dto.UsuarioDto;
import adv.model.entities.Usuario;
import adv.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private UsuarioService clienteService;
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/lista")
	@Operation(summary = "Este metodo devuelve una lista de todos los Usuarios con el rol de cliente existentes")
	public List<Usuario> listaTodos() {
		return clienteService.listaClientes();
	}

	@GetMapping("/uno/{id}")
	@Operation(summary = "Este metodo devuelve un usuarios con el rol de cliente cuando le pasamos un idCliente")
	public Usuario uno(@PathVariable("id") @Parameter(description = "Identificador de Cliente") int idCliente) {
		return clienteService.findOne(idCliente);
	}

	@PostMapping("/alta")
	@Operation(summary = "Alta de Cliente", description = "Endpoint para dar de alta a un nuevo cliente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Cliente dado de alta exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDto.class))),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
	public ResponseEntity<?> altaCliente(@RequestBody UsuarioDto clienteDto) {
		Usuario cliente = new Usuario();
		modelMapper.map(clienteDto, cliente);
		if (clienteService.altaTecnico(cliente) != null) {
			clienteDto.setIdUsuario(cliente.getIdUsuario());
			return ResponseEntity.status(201).body(clienteDto);
		} else {
			String mensaje = "Alta No realizada";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
		}

	}
	
	@PostMapping("/modificar")
	public Usuario updateUsuario(@RequestBody ModificarUsuarioDto modificarUsuarioDto) {
		
		Usuario usuarioModificado = new Usuario();
		Usuario usuario = clienteService.findOne(modificarUsuarioDto.getIdUsuario());
		
		usuarioModificado.setIdUsuario(modificarUsuarioDto.getIdUsuario());
		usuarioModificado.setUsername(modificarUsuarioDto.getUsername());
		usuarioModificado.setNombre(modificarUsuarioDto.getNombre());
		usuarioModificado.setApellidos(modificarUsuarioDto.getApellidos());
		usuarioModificado.setDireccion(modificarUsuarioDto.getDireccion());
		
		usuarioModificado.setPassword(usuario.getPassword());
		usuarioModificado.setRoles(usuario.getRoles());
		usuarioModificado.setZona(usuario.getZona());
		usuarioModificado.setProductoEnAlmacen(usuario.getProductoEnAlmacen());
		
		return clienteService.updateUsuario(usuarioModificado);
	}
	

}
