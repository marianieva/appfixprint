package adv.controller;

import java.util.List;

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
import adv.model.dto.PedidoDto;
import adv.model.entities.Pedido;
import adv.service.PedidoService;
import jakarta.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/pedido")
public class PedidoController {
	

	@Autowired
	private PedidoService pedidoService;


	@GetMapping("/lista")
    @Operation(summary = "Lista todos los pedidos", description = "Obtiene una lista de todos los pedidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pedido.class)))
    })
    public ResponseEntity<List<Pedido>> listaPedidos() {
        List<Pedido> pedidos = pedidoService.listaPedidos();
        return ResponseEntity.ok(pedidos);
    }

	@GetMapping("/uno/{id}")
	@Operation(summary = "Obtener un pedido", description = "Obtiene los detalles de un pedido por su ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Detalles del pedido obtenidos exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pedido.class))),
			@ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
	public ResponseEntity<Pedido> obtenerPedido(@Parameter(description = "ID del pedido", required = true) @PathVariable("id") int idPedido) {
		Pedido pedido = pedidoService.findOne(idPedido);
        if (pedido != null) {
            return ResponseEntity.ok(pedido);
        } else {
            return ResponseEntity.notFound().build();
        }
	}

	@Transactional
	@PostMapping("/alta")
	@Operation(summary = "Alta de Pedido", description = "Endpoint para dar de alta un nuevo pedido")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Pedido dado de alta exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoDto.class))),
			@ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
	public ResponseEntity<?> altaPedido(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del pedido a dar de alta", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoDto.class))) @RequestBody PedidoDto pedidoDto) {

		if (pedidoService.altaPedido(pedidoDto) != null) {
			return ResponseEntity.status(201).body(pedidoDto);
		} else {
			String mensaje = "Alta No realizada";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
		}

	}

	@PutMapping("/confirmar/{id}")
	@Operation(summary = "Confirmar Pedido", description = "Marca un pedido como confirmado")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Pedido confirmado exitosamente"),
			@ApiResponse(responseCode = "400", description = "No se pudo confirmar el pedido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))) })
	public ResponseEntity<?> confirmarPedido(
			@Parameter(description = "ID del pedido", required = true) @PathVariable("id") int idPedido) {

		if (pedidoService.confirmarPedido(idPedido)) {
			return ResponseEntity.status(201).body("OK");
		} else {
			String mensaje = "El pedido no se ha podido confirmar";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensaje);
		}
	}

	@GetMapping("/usuario/{id}")
    @Operation(summary = "Obtener pedidos por usuario", description = "Obtiene una lista de pedidos asociados a un usuario específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoDto.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<List<PedidoDto>> pedidoPorUsuario(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable("id") int idUsuario) {
        
        List<PedidoDto> pedidosDto = pedidoService.listaPedidosPorUsuarioDto(idUsuario);
        if (pedidosDto.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(pedidosDto);
        }
    }

	
	@GetMapping("/recambios")
	@Operation(summary = "Obtener pedidos recambio", description = "Obtiene una lista de pedidos realizados por los tecnicos, solo contienen productos de tipo recambio")
	@ApiResponse(responseCode = "200", description = "Lista de pedidos recambios obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pedido.class)))
    public ResponseEntity<List<Pedido>> pedidosRecambios(){
        return ResponseEntity.ok(pedidoService.listaPedidosRecambios());
    }

    @GetMapping("/consumibles")
    @Operation(summary = "Obtener pedidos consumibles", description = "Obtiene una lista de pedidos realizados por los cliente, solo contienen productos de tipo consumible")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos recambios obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pedido.class)))
    public ResponseEntity<List<Pedido>> pedidosConsumibles(){
        return ResponseEntity.ok(pedidoService.listaPedidosConsumibles());
        
    }

}
