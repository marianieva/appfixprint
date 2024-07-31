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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import adv.model.dto.IncidenciaCompletaDto;
import adv.model.dto.IncidenciaDto;
import adv.model.entities.Incidencia;
import adv.service.ImpresoraService;
import adv.service.IncidenciaService;
import adv.service.UsuarioService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/incidencia")
public class IncidenciaController {

	@Autowired
	private IncidenciaService incidenciaService;
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/lista")
	@Operation(summary = "Lista todas las incidencias", description = "Obtiene una lista de todas las incidencias")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de incidencias obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Incidencia.class))) })

	public List<IncidenciaDto> listaTodos() {
		return incidenciaService.listaIncidenciaDto();
	}

	@GetMapping("/una/{id}")
	@Operation(summary = "Obtener una incidencia", description = "Obtiene los detalles de una incidencia por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Detalles de la incidencia obtenidos exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Incidencia.class))),
			@ApiResponse(responseCode = "404", description = "Incidencia no encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
	public IncidenciaCompletaDto una(
			@Parameter(description = "ID de la incidencia", required = true) 
			@PathVariable("id") int idIncidencia) {
		return incidenciaService.findOneDto(idIncidencia);
	}

	@Transactional
	@PostMapping("/alta")
	@Operation(summary = "Alta de Incidencia", description = "Endpoint para dar de alta una nueva incidencia")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Incidencia dada de alta exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = IncidenciaDto.class))),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
	public ResponseEntity<?> altaIncidencia(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la incidencia a dar de alta", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = IncidenciaDto.class))) 
			@RequestBody IncidenciaDto incidenciaDto) {
		Incidencia i = new Incidencia();
		modelMapper.map(incidenciaDto, i);
		if (incidenciaService.altaIncidencia(i) != null) {
			incidenciaDto.setIdIncidencia(i.getIdIncidencia());
			return ResponseEntity.status(201).body(incidenciaDto);
		} else {
			String mensaje = "Alta No realizada";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
		}
	}

	@PutMapping("/iniciar/{id}")
	@Operation(summary = "Iniciar Incidencia", description = "Marca una incidencia como iniciada")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "201", description = "Incidencia iniciada exitosamente"),
			@ApiResponse(responseCode = "400", description = "No se pudo iniciar la incidencia", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
	public ResponseEntity<?> iniciarIncidenica(
			@Parameter(description = "ID de la incidencia", required = true) 
			@PathVariable("id") int idIncidencia) {

		if (incidenciaService.iniciarIncidencia(idIncidencia)) {
			return ResponseEntity.status(201).body("OK");
		} else {
			String mensaje = "La incidencia no ha podido iniciarse";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
		}
	}

	@PutMapping("/cerrar")
	@Operation(summary = "Cerrar Incidencia", description = "Marca una incidencia como cerrada")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "201", description = "Incidencia cerrada exitosamente"),
			@ApiResponse(responseCode = "400", description = "No se pudo cerrar la incidencia", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
	public ResponseEntity<?> cerrarIncidenica(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la incidencia a cerrar", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = IncidenciaDto.class))) 
			@RequestBody IncidenciaCompletaDto incidenciaCompletaDto) {
		if (incidenciaService.cerrarIncidencia(incidenciaCompletaDto)) {
			return ResponseEntity.status(200).body(0);
		} else {
			String mensaje = "La incidencia no se ha podido cerrar";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
		}
	}

	@PutMapping("/cancelar/{id}")
	@Operation(summary = "Cancelar Incidencia", description = "Marca una incidencia como cancelada")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "201", description = "Incidencia cancelada exitosamente"),
			@ApiResponse(responseCode = "400", description = "No se pudo cancelar la incidencia", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
	public ResponseEntity<?> cancelarIncidenica(
			@Parameter(description = "ID de la incidencia", required = true) 
			@PathVariable("id") int idIncidencia) {

		if (incidenciaService.cancelarIncidencia(idIncidencia)) {
			return ResponseEntity.status(201).body("OK");
		} else {
			String mensaje = "La incidencia no ha podido cancelarse";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
		}
	}

	/*
	 * @GetMapping("/productos/{id}")
	 * 
	 * @Operation(summary = "Obtener productos en una incidencia", description =
	 * "Obtiene una lista de productos asociados a una incidencia específica")
	 * 
	 * @ApiResponses(value = {
	 * 
	 * @ApiResponse(responseCode = "200", description =
	 * "Lista de productos obtenida exitosamente", content = @Content(mediaType =
	 * "application/json", schema = @Schema(implementation = Producto.class))),
	 * 
	 * @ApiResponse(responseCode = "404", description = "Incidencia no encontrada",
	 * content = @Content(mediaType = "application/json", schema
	 * = @Schema(implementation = String.class))) }) public List<Producto>
	 * productosEnIncidencias(
	 * 
	 * @Parameter(description = "ID de la incidencia", required = true)
	 * 
	 * @PathVariable("id") int id) { return
	 * incidenciaService.productosEnIncicencias(id); }
	 */

	@GetMapping("/usuario/{id}")
	@Operation(summary = "Obtener incidencias por usuario", description = "Obtiene una lista de incidencias asociadas a un usuario específico")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de incidencias obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Incidencia.class))),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
	public List<IncidenciaCompletaDto> incidenciasPorUsuario(
			@Parameter(description = "ID del usuario", required = true) 
			@PathVariable("id") int idUsuario) {
		return incidenciaService.listaIncidenciaPorUsuario(idUsuario);
	}
	
	@GetMapping("/tecnico/{id}")
	@Operation(summary = "Obtener incidencias por usuario", description = "Obtiene una lista de incidencias asociadas a un usuario específico")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de incidencias obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Incidencia.class))),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
	public List<IncidenciaCompletaDto> incidenciasPorTecnico(
			@Parameter(description = "ID del usuario", required = true) 
			@PathVariable("id") int idUsuario) {
		return incidenciaService.listaIncidenciaPorTecnico(idUsuario);
	}

	@GetMapping("/impresora/{id}")
	@Operation(summary = "Obtener incidencias por impresora", description = "Obtiene una lista de incidencias asociadas a una impresora específica")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de incidencias obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Incidencia.class))),
			@ApiResponse(responseCode = "404", description = "Impresora no encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
	public List<Incidencia> incidenciasPorImpresora(
			@Parameter(description = "Número de serie de la impresora", required = true)
			@PathVariable("id") int serialNumber) {
		return incidenciaService.listaIncidenciaPorImpresora(serialNumber);
	}

	@GetMapping("/finalizdas")
	@Operation(summary = "Obtener incidencias finalizadas", description = "Obtiene una lista de todas las incidencias que han sido finalizadas")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de incidencias finalizadas obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Incidencia.class))) })
	public List<IncidenciaCompletaDto> incidenciasFinalizadas() {
		return incidenciaService.listaIncidenciasFinalizadas();
	}

	@GetMapping("/pendientes")
	@Operation(summary = "Obtener incidencias pendientes", description = "Obtiene una lista de todas las incidencias que están pendientes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de incidencias pendientes obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Incidencia.class))) })
	public List<IncidenciaCompletaDto> incidenciasPendientes() {
		return incidenciaService.listaIncidenciasPendientes();
	}
	
	@GetMapping("/encurso")
	@Operation(summary = "Obtener incidencias pendientes", description = "Obtiene una lista de todas las incidencias que están pendientes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de incidencias pendientes obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Incidencia.class))) })
	public List<IncidenciaCompletaDto> incidenciasEnCurso() {
		return incidenciaService.listaIncidenciasEnCurso();
	}

}
