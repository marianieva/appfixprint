package adv.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import adv.model.dto.DetallePedidoDto;
import adv.model.dto.PedidoDto;
import adv.model.entities.DetallePedido;
import adv.model.entities.Pedido;
import adv.model.entities.Producto;
import adv.model.enums.StatusPedido;
import adv.model.enums.TipoPedido;
import adv.repository.PedidoRepository;
import jakarta.transaction.Transactional;

@Service
public class PedidoServiceImpl implements PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private ProductoService productoService;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<Pedido> listaPedidos() {
		return pedidoRepository.findAll();
	}

	@Override
	public Pedido findOne(int idPedido) {
		return pedidoRepository.findById(idPedido).orElse(null);
	}

	@Override
	public Pedido createPedido(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}

	@Override
	public Pedido updateOne(Pedido pedido) {
		try {
			return pedidoRepository.save(pedido);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Pedido altaPedido(PedidoDto pedidoDto) {
		Pedido p = new Pedido();
		modelMapper.map(pedidoDto, p);

		for (DetallePedidoDto detalleDto : pedidoDto.getDetallesDto()) {
			Producto producto = productoService.findOne(detalleDto.getIdProducto());
			if (producto != null) {
				DetallePedido detallePedido = new DetallePedido();
				detallePedido.setProducto(producto);
				detallePedido.setCantidad(detalleDto.getCantidad());
				p.addDetallePedido(detallePedido);
			}
		}
		p.setEstado(StatusPedido.PENDIENTE);
		p.setFechaPedido(new Date());
		return createPedido(p);
	}

	@Override
	public boolean confirmarPedido(int idPedido) {
		Optional<Pedido> optionalPedido = pedidoRepository.findById(idPedido);
		if (optionalPedido.isPresent()) {
			Pedido pedido = optionalPedido.get();
			for (DetallePedido detalle : pedido.getDetallePedidos()) {
				Producto producto = detalle.getProducto();
				if (producto.getStock() < detalle.getCantidad()) {
					System.out.println("Stock insuficiente");
					return false;
				}
				producto.setStock(producto.getStock() - detalle.getCantidad());
				productoService.updateProducto(producto);
			}
			pedido.setEstado(StatusPedido.ENVIADO);
			usuarioService.actualizarStockUsuario(pedido);
			pedidoRepository.save(pedido);
			System.out.println("Pedido aprobado");
			return true;
		}
		System.out.println("Pedido no encontrado");
		return false;
	}

	@Override
	public List<Pedido> listaPedidosPorUsuario(int idUsuario) {
		List<Pedido> todosPedidos = this.listaPedidos();
		List<Pedido> pedidosUsuario = new ArrayList<>();

		for (Pedido pedido : todosPedidos) {
			if (pedido.getUsuario().equals(usuarioService.findOne(idUsuario))) {
				pedidosUsuario.add(pedido);
			}
		}

		return pedidosUsuario;
    }
	
	@Override
	public List<PedidoDto> listaPedidosPorUsuarioDto(int idUsuario) {
        List<Pedido> pedidos = listaPedidosPorUsuario(idUsuario);
        List<PedidoDto> pedidosDto = new ArrayList<>();
        
        for (Pedido pedido : pedidos) {
            PedidoDto pedidoDto = modelMapper.map(pedido, PedidoDto.class);
            pedidoDto.setDetalles(pedido.getDetallePedidos());
            pedidosDto.add(pedidoDto);
        }
        return pedidosDto;
    }

	
	
    @Override
    public List<Pedido> listaPedidosRecambios() {
        List<Pedido> todos = this.listaPedidos();
        List<Pedido> pedidosRecambios = new ArrayList<>();

        for (Pedido pedido: todos) {
            if(pedido.getTipoPedido().equals(TipoPedido.TECNICO)) {
                pedidosRecambios.add(pedido);
            }
        }
        return pedidosRecambios;
    }

  
    @Override
    public List<Pedido> listaPedidosConsumibles() {
        List<Pedido> todos = this.listaPedidos();
        List<Pedido> pedidosRecambios = new ArrayList<>();

        for (Pedido pedido: todos) {
            if(pedido.getTipoPedido().equals(TipoPedido.CLIENTE)) {
                pedidosRecambios.add(pedido);
            }
        }
        return pedidosRecambios;
    }


}
