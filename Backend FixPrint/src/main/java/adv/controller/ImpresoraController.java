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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import adv.model.dto.ImpresoraDto;
import adv.model.entities.Impresora;
import adv.service.ImpresoraService;
import adv.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/impresora")
public class ImpresoraController {

	@Autowired
	private ImpresoraService impresoraService;
	@Autowired
	private UsuarioService clienteService;
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/lista")
	@Operation(summary = "Lista todas las impresoras", description = "Obtiene una lista de todas las impresoras")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de impresoras obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Impresora.class))) })
	public List<Impresora> listaTodos() {
		return impresoraService.listaImpresoras();
	}

	@GetMapping("/una/{id}")
	@Operation(summary = "Obtener una impresora", description = "Obtiene los detalles de una impresora por su número de serie")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Detalles de la impresora obtenidos exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Impresora.class))),
			@ApiResponse(responseCode = "404", description = "Impresora no encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
	public Impresora una(
			@Parameter(description = "Número de serie de la impresora", required = true) @PathVariable("id") int serialNumber) {
		return impresoraService.findOne(serialNumber);
	}

	@PostMapping("/alta")
	@Operation(summary = "Alta de Impresora", description = "Endpoint para dar de alta a una nueva impresora")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Impresora dada de alta exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ImpresoraDto.class))),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
	public ResponseEntity<?> altaImpresora(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la impresora a dar de alta", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ImpresoraDto.class)))
			@RequestBody ImpresoraDto impresoraDto) {
		Impresora i = new Impresora();
		modelMapper.map(impresoraDto, i);
		i.setUsuario(clienteService.findOneCliente(impresoraDto.getUsuario()));
		if (impresoraService.altaImpresora(i) != null) {
			impresoraDto.setSerialNumber(impresoraDto.getSerialNumber());
			return ResponseEntity.status(201).body(impresoraDto);
		} else {
			String mensaje = "Alta No realizada";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
		}
	}

	@GetMapping("/cliente/{id}")
	@Operation(summary = "Obtener impresoras por cliente", description = "Obtiene una lista de impresoras asociadas a un cliente específico")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de impresoras obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Impresora.class))),
			@ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
	public List<Impresora> impresoraPorCliente(
			@Parameter(description = "ID del cliente", required = true) 
			@PathVariable("id") int idCliente) {
		return impresoraService.listaImpresorasPorCliente(idCliente);
	}

}
