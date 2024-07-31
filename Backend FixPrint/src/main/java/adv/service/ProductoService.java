package adv.service;

import java.util.List;

import adv.model.entities.Producto;

public interface ProductoService {

	/**
	 * Método para obtener todos los productos.
	 *
	 * @return Una lista de todos los productos.
	 */
	List<Producto> listaProductos();

	/**
	 * Método para encontrar varios productos por sus identificadores.
	 *
	 * @param list La lista de identificadores de los productos a buscar.
	 * @return Una lista de productos encontrados.
	 */
	List<Producto> findAllById(List<Integer> list);

	/**
	 * Método para dar de alta un nuevo producto.
	 *
	 * @param producto El producto a dar de alta.
	 * @return El producto dado de alta.
	 */
	Producto altaProducto(Producto producto);
	
	/**
	 * Método para modificar un producto.
	 *
	 * @param producto El producto a modificar.
	 * @return El producto modificado.
	 */
	Producto updateProducto(Producto producto);

	/**
	 * Método para encontrar un producto por su identificador.
	 *
	 * @param idProducto El identificador del producto a buscar.
	 * @return El producto encontrado o nulo si no existe.
	 */
	Producto findOne(int idProducto);

	/**
	 * Método para obtener todos los productos de una marca específica.
	 *
	 * @param marca La marca de los productos a buscar.
	 * @return Una lista de productos de la marca especificada.
	 */
	List<Producto> productoPorMarca(String marca);
	
	List<Producto> listaProductosRepuesto();
	
	List<Producto> listaProductosConsumbile();
	
	List<Producto> listaProductosRepuestoYMarca(String marca);
	
	List<Producto> listaProductosConsumbileYMarca(String marca);


}
