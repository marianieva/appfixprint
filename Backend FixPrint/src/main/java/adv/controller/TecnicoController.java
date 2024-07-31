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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import adv.model.dto.UsuarioDto;
import adv.model.entities.Producto;
import adv.model.entities.Usuario;
import adv.service.UsuarioService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tecnico")
public class TecnicoController {

	@Autowired
	private UsuarioService tecnicoService;
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/lista")
	@Operation(summary = "Lista todos los técnicos", description = "Obtiene una lista de todos los técnicos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de técnicos obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))) })
	public List<Usuario> listaTodos() {
		return tecnicoService.listaTecnicos();
	}

	@GetMapping("/uno/{id}")
	@Operation(summary = "Obtener un técnico", description = "Obtiene los detalles de un técnico por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Detalles del técnico obtenidos exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
			@ApiResponse(responseCode = "404", description = "Técnico no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
	public Usuario una(@Parameter(description = "ID del técnico", required = true) @PathVariable("id") int idTecnico) {
		return tecnicoService.findOne(idTecnico);
	}

	@PostMapping("/alta")
	@Operation(summary = "Alta de Técnico", description = "Endpoint para dar de alta un nuevo técnico")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Técnico dado de alta exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDto.class))),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
	public ResponseEntity<?> altaTecnico(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del técnico a dar de alta", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDto.class))) @RequestBody UsuarioDto tecnicoDto) {
		Usuario tecnico = new Usuario();
		modelMapper.map(tecnicoDto, tecnico);
		if (tecnicoService.altaTecnico(tecnico) != null) {
			tecnicoDto.setIdUsuario(tecnico.getIdUsuario());
			return ResponseEntity.status(201).body(tecnicoDto);
		} else {
			String mensaje = "Alta No realizada";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
		}

	}

	@GetMapping("/zona/{id}")
	@Operation(summary = "Obtener técnico por zona", description = "Obtiene un técnico asociado a una zona específica")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Técnico obtenido exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
			@ApiResponse(responseCode = "404", description = "Zona no encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
	public Usuario tecnicoPorZona(
			@Parameter(description = "ID de la zona", required = true)
			@PathVariable("id") int zona) {
		return tecnicoService.tecnicoPorZona(zona);
	}
	
	@GetMapping("/productos/{id}")
	@Operation(summary = "Obtener productos por usuario", description = "Obtiene una lista de productos asociados a un usuario específico")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
	public List<Producto> productoPorUsuario(
			@Parameter(description = "ID del usuario", required = true) 
			@PathVariable("id") int idUsuario) {
		return tecnicoService.productosPorUsuario(idUsuario);
	}

}
