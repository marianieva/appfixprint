package adv.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import adv.model.entities.Impresora;
import adv.repository.ImpresoraRepository;

@Service
public class ImpresoraServiceImpl implements ImpresoraService {

	@Autowired
	ImpresoraRepository impresoraRepository;

	@Override
	public List<Impresora> listaImpresoras() {
		return impresoraRepository.findAll();
	}

	@Override
	public Impresora findOne(int serialNumber) {
		return impresoraRepository.findById(serialNumber).orElse(null);
	}

	@Override
	public Impresora updateOne(Impresora impresora) {
		try {
			return impresoraRepository.save(impresora);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Impresora altaImpresora(Impresora impresora) {
		return impresoraRepository.save(impresora);
	}

	@Override
	public List<Impresora> listaImpresorasPorCliente(int idCliente) {
		List<Impresora> impresoras = this.listaImpresoras();
		List<Impresora> impresorasCliente = new ArrayList<>();
		for (Impresora impresora : impresoras) {
			if (impresora.getUsuario().getIdUsuario() == idCliente) {
				impresorasCliente.add(impresora);
			}
		}
		return impresorasCliente;
	}
}
