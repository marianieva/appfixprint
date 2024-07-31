package adv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import adv.model.entities.Producto;
import adv.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	ProductoRepository productoRepository;

	@Override
	public List<Producto> listaProductos() {
		return productoRepository.findAll();
	}

	@Override
	public Producto altaProducto(Producto producto) {
		return productoRepository.save(producto);
	}
	
	@Override
	public Producto updateProducto(Producto producto) {
		return productoRepository.save(producto);
	}

	@Override
	public Producto findOne(int idProducto) {
		return productoRepository.findById(idProducto).orElse(null);
	}

	@Override
	public List<Producto> findAllById(List<Integer> list) {
		return productoRepository.findAllById(list);
	}

	@Override
	public List<Producto> productoPorMarca(String marca) {
		return productoRepository.productosPorMarca(marca);
	}

	@Override
	public List<Producto> listaProductosRepuesto() {
		return productoRepository.productosPorRepuesto();
	}

	@Override
	public List<Producto> listaProductosConsumbile() {
		return productoRepository.productosPorConsumible();
	}

	@Override
	public List<Producto> listaProductosRepuestoYMarca(String marca) {
		return productoRepository.productosPorRepuestoYMarca(marca);
	}

	@Override
	public List<Producto> listaProductosConsumbileYMarca(String marca) {
		return productoRepository.productosConsumibleYMarca(marca);
	}


}
