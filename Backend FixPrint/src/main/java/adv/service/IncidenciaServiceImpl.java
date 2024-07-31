package adv.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import adv.model.dto.DetallePedidoDto;
import adv.model.dto.IncidenciaCompletaDto;
import adv.model.dto.IncidenciaDto;
import adv.model.dto.ProductoEnIncidenciaDto;
import adv.model.entities.AlmacenUsuario;
import adv.model.entities.Impresora;
import adv.model.entities.Incidencia;
import adv.model.entities.Producto;
import adv.model.entities.ProductoEnIncidencia;
import adv.model.entities.Usuario;
import adv.model.enums.StatusIncidencia;
import adv.repository.AlmacenUsuarioRepository;
import adv.repository.IncidenciaRepository;
import adv.repository.ProductoEnIncidenciaRepository;
import jakarta.transaction.Transactional;

@Service
public class IncidenciaServiceImpl implements IncidenciaService {

	@Autowired
	private IncidenciaRepository incidenciaRepository;
	@Autowired
	private ProductoEnIncidenciaRepository productoEnIncidenciaRepository;
	@Autowired
	private AlmacenUsuarioRepository almacenUsuarioRepository;
	@Autowired
	private ProductoService productoService;
	@Autowired
	private ImpresoraService impresoraService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<Incidencia> listaIncidencia() {
		return incidenciaRepository.findAll();
	}

	@Override
	public List<IncidenciaDto> listaIncidenciaDto() {
		List<IncidenciaDto> incidenciasDto = new ArrayList<>();
		List<Incidencia> incidenciasList = incidenciaRepository.findAll();

		for (Incidencia incidencia : incidenciasList) {
			IncidenciaDto incidenciaDto = modelMapper.map(incidencia, IncidenciaDto.class);
			incidenciaDto.setMarca(impresoraService.findOne(incidencia.getSerialNumber().getSerialNumber()).getMarca());
			incidenciaDto
					.setModelo(impresoraService.findOne(incidencia.getSerialNumber().getSerialNumber()).getModelo());
			incidenciasDto.add(incidenciaDto);
		}
		return incidenciasDto;
	}

	@Override
	public Incidencia updateOne(Incidencia incidencia) {

		try {
			return incidenciaRepository.save(incidencia);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Incidencia findOne(int idIncidencia) {
		return incidenciaRepository.findById(idIncidencia).orElse(null);
	}

	@Override
	public IncidenciaCompletaDto findOneDto(int idIncidencia) {
		Incidencia incidencia = incidenciaRepository.findById(idIncidencia).orElse(null);
		IncidenciaCompletaDto incidenciaCompletaDto = new IncidenciaCompletaDto();
		modelMapper.map(incidencia, incidenciaCompletaDto);

		return incidenciaCompletaDto;
	}

	@Override
	public Incidencia altaIncidencia(Incidencia incidencia) {

		Impresora imp = impresoraService.findOne(incidencia.getSerialNumber().getSerialNumber());
		incidencia.setTecnico(usuarioService.tecnicoPorZona(imp.getUsuario().getZona().getIdZona())); // ZONAS
																										// ALEATORIAS
		incidencia.setEstado(StatusIncidencia.PENDIENTE);
		incidencia.setFechaAlta(new Date());
		return incidenciaRepository.save(incidencia);
	}

	@Override
	public boolean iniciarIncidencia(int idIncidencia) {
		Incidencia incidencia = findOne(idIncidencia);
		if (incidencia != null) {
			incidencia.setFechaInicio(new Date());
			incidencia.setEstado(StatusIncidencia.ENCURSO);
			updateOne(incidencia);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean cerrarIncidencia(IncidenciaCompletaDto incidencia) {
		
		Incidencia i = incidenciaRepository.findById(incidencia.getIdIncidencia()).orElse(null);
		if (i != null) {
			i.setEstado(StatusIncidencia.TERMINADA);
			i.setFechaFin(new Date());
			i.setComentarioTecnico(incidencia.getComentarioTecnico());

			Usuario usuario = usuarioService.findOne(i.getTecnico().getIdUsuario());
			List<AlmacenUsuario> almacenUsuarios = almacenUsuarioRepository.findByUsuario(usuario);

			for (AlmacenUsuario almacenUsuario : almacenUsuarios) {
				// Producto producto = almacenUsuario.getProducto();
				int stock = almacenUsuario.getStock();

				if (stock > 0) {

					// Agregar productos a la incidencia
					List<ProductoEnIncidenciaDto> productosEnIncidencia = incidencia.getProductosEnIncidencia();
					for (ProductoEnIncidenciaDto prod : productosEnIncidencia) {
						ProductoEnIncidencia productoEnIncidencia = new ProductoEnIncidencia();
					//	modelMapper.map(prod, productoEnIncidencia);
						productoEnIncidencia.setProducto(productoService.findOne(prod.getIdProducto()));
						productoEnIncidencia.setCantidad(prod.getCantidad());
						productoEnIncidencia.setIncidencia(i);
						productoEnIncidenciaRepository.save(productoEnIncidencia);
						

						// Restar cantidad del almacén del usuario
						almacenUsuario.setStock(almacenUsuario.getStock() - prod.getCantidad());
						almacenUsuarioRepository.save(almacenUsuario);
					}
				} else {
					return false;
				}
			}

			incidenciaRepository.save(i);
			return true;
		} else {
			return false;
		}

	}

	/*
	 * @Override public boolean cerrarIncidencia(IncidenciaCompletaDto incidencia) {
	 * 
	 * Incidencia i =
	 * incidenciaRepository.findById(incidencia.getIdIncidencia()).orElse(null);
	 * 
	 * if (i != null) { i.setEstado(StatusIncidencia.TERMINADA); i.setFechaFin(new
	 * Date()); i.setComentarioTecnico(incidencia.getComentarioTecnico());
	 * 
	 * incidenciaRepository.save(i); return true; } else { return false; }
	 * 
	 * }
	 */

	@Override
	public boolean cancelarIncidencia(int idIncidencia) {
		Incidencia incidencia = findOne(idIncidencia);
		if (incidencia != null) {
			incidencia.setEstado(StatusIncidencia.CANCELADA);
			updateOne(incidencia);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<IncidenciaCompletaDto> listaIncidenciaPorUsuario(int idUsuario) {
		List<IncidenciaCompletaDto> listaIncidenciaCompletaDto = new ArrayList<>();
		for (Incidencia incidencia : incidenciaRepository.incidenciasPorUsuario(idUsuario)) {
			IncidenciaCompletaDto incidenciaCompletaDto = new IncidenciaCompletaDto();
			modelMapper.map(incidencia, incidenciaCompletaDto);
			incidenciaCompletaDto
					.setMarca(impresoraService.findOne(incidencia.getSerialNumber().getSerialNumber()).getMarca());
			incidenciaCompletaDto
					.setModelo(impresoraService.findOne(incidencia.getSerialNumber().getSerialNumber()).getModelo());
			listaIncidenciaCompletaDto.add(incidenciaCompletaDto);
		}

		return listaIncidenciaCompletaDto;

	}

	@Override
	public List<IncidenciaCompletaDto> listaIncidenciaPorTecnico(int idUsuario) {
		List<IncidenciaCompletaDto> listaIncidenciaCompletaDto = new ArrayList<>();
		for (Incidencia incidencia : incidenciaRepository.incidenciasPorTecnico(idUsuario)) {
			IncidenciaCompletaDto incidenciaCompletaDto = new IncidenciaCompletaDto();
			modelMapper.map(incidencia, incidenciaCompletaDto);
			incidenciaCompletaDto
					.setMarca(impresoraService.findOne(incidencia.getSerialNumber().getSerialNumber()).getMarca());
			incidenciaCompletaDto
					.setModelo(impresoraService.findOne(incidencia.getSerialNumber().getSerialNumber()).getModelo());
			listaIncidenciaCompletaDto.add(incidenciaCompletaDto);
		}

		return listaIncidenciaCompletaDto;

	}

	@Override
	public List<Incidencia> listaIncidenciaPorImpresora(int serialNumber) {
		return incidenciaRepository.incidenciasPorImpresora(serialNumber);
	}

	@Override
	public List<IncidenciaCompletaDto> listaIncidenciasFinalizadas() {
		List<Incidencia> todas = this.listaIncidencia();
		List<IncidenciaCompletaDto> incidenciasFinalizadas = new ArrayList<>();

		for (Incidencia incidencia : todas) {
			if (incidencia.getEstado().equals(StatusIncidencia.TERMINADA)) {
				IncidenciaCompletaDto incidenciaFinalizada = new IncidenciaCompletaDto();
				modelMapper.map(incidencia, incidenciaFinalizada);
				incidenciasFinalizadas.add(incidenciaFinalizada);
			}
		}

		return incidenciasFinalizadas;
	}

	@Override
	public List<IncidenciaCompletaDto> listaIncidenciasPendientes() {
		List<Incidencia> todas = this.listaIncidencia();
		List<IncidenciaCompletaDto> incidenciasPendientes = new ArrayList<>();

		for (Incidencia incidencia : todas) {
			if (incidencia.getEstado().equals(StatusIncidencia.PENDIENTE)) {
				IncidenciaCompletaDto incidenciaPendiente = new IncidenciaCompletaDto();
				modelMapper.map(incidencia, incidenciaPendiente);
				incidenciaPendiente
						.setMarca(impresoraService.findOne(incidencia.getSerialNumber().getSerialNumber()).getMarca());
				incidenciasPendientes.add(incidenciaPendiente);
			}
		}

		return incidenciasPendientes;
	}
	
	@Override
	public List<IncidenciaCompletaDto> listaIncidenciasEnCurso() {
		List<Incidencia> todas = this.listaIncidencia();
		List<IncidenciaCompletaDto> incidenciasPendientes = new ArrayList<>();

		for (Incidencia incidencia : todas) {
			if (incidencia.getEstado().equals(StatusIncidencia.ENCURSO)) {
				IncidenciaCompletaDto incidenciaPendiente = new IncidenciaCompletaDto();
				modelMapper.map(incidencia, incidenciaPendiente);
				incidenciaPendiente
						.setMarca(impresoraService.findOne(incidencia.getSerialNumber().getSerialNumber()).getMarca());
				incidenciasPendientes.add(incidenciaPendiente);
			}
		}

		return incidenciasPendientes;
	}

	@Override
	public void asignarProductoAIncidencia(int idIncidencia, int idProducto, int cantidad) {
		Incidencia incidencia = incidenciaRepository.findById(idIncidencia)
				.orElseThrow(() -> new IllegalArgumentException("Incidencia no encontrada"));

		Producto producto = productoService.findOne(idProducto);
		// .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

		Usuario usuario = incidencia.getSerialNumber().getUsuario();

		AlmacenUsuario almacenUsuario = almacenUsuarioRepository.findByUsuarioAndProducto(usuario, producto);
		// .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado en el
		// almacén del usuario"));

		if (almacenUsuario.getStock() < cantidad) {
			throw new IllegalArgumentException("No hay suficiente stock en el almacén del usuario");
		}

		almacenUsuario.setStock(almacenUsuario.getStock() - cantidad);
		almacenUsuarioRepository.save(almacenUsuario);

		ProductoEnIncidencia productoEnIncidencia = new ProductoEnIncidencia();
		productoEnIncidencia.setProducto(producto);
		productoEnIncidencia.setIncidencia(incidencia);
		productoEnIncidencia.setCantidad(cantidad);

		productoEnIncidenciaRepository.save(productoEnIncidencia);
	}

}
