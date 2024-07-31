package adv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import adv.model.entities.Producto;
import adv.service.ProductoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/productos")
public class ProductoController {

	@Autowired
	private ProductoService productoService;

	@GetMapping("/lista")
	@Operation(summary = "Lista todos los productos", description = "Obtiene una lista de todos los productos disponibles")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))) })
	public List<Producto> todosProductos() {
		return productoService.listaProductos();
	}
	
	@GetMapping("/lista/marca/{marca}")
	@Operation(summary = "Lista productos de tipo consumible", description = "Obtiene una lista de todos los productos consumibles, estos productos solo pueden hacer pedido los cliente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))) })
	public List<Producto> todosProductosPorMarca(@PathVariable("marca") @Parameter(description = "Marca del producto") String marca) {
		return productoService.productoPorMarca(marca);
	}
	
	@GetMapping("/lista/repuestos")
	@Operation(summary = "Lista productos de tipo repuesto", description = "Obtiene una lista de todos los productos repuestos, estos productos solo pueden hacer pedido los tecnicos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))) })
	public List<Producto> todosProductosRepuesto() {
		return productoService.listaProductosRepuesto();
	}
	
	@GetMapping("/lista/repuestos/{marca}")
	@Operation(summary = "Lista productos de tipo consumible por marca", description = "Obtiene una lista de todos los productos consumibles filtrado por marca estos productos solo pueden hacer pedido los cliente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))) })
	public List<Producto> todosProductosPorRepuestosYMarca(@PathVariable("marca") @Parameter(description = "Marca del producto") String marca) {
		return productoService.listaProductosRepuestoYMarca(marca);
	}
	
	
	@GetMapping("/lista/consumibles/{marca}")
	@Operation(summary = "Lista productos de tipo consumible por marca", description = "Obtiene una lista de todos los productos consumibles filtrado por marca estos productos solo pueden hacer pedido los cliente")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))) })
	public List<Producto> todosProductosPorConsumiblesYMarca(@PathVariable("marca") @Parameter(description = "Marca del producto") String marca) {
		return productoService.listaProductosConsumbileYMarca(marca);
	}
	
	

}
