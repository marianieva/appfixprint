package adv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import adv.model.entities.Zona;
import adv.repository.ZonaRepository;

@Service
public class ZonaServiceImpl implements ZonaService{
	
	@Autowired
	ZonaRepository zonaRepository;

	@Override
	public Zona findOne(int idZona) {
		return zonaRepository.findById(idZona).orElse(null);
	}

	@Override
	public List<Zona> findAllZona() {
		return zonaRepository.findAll();
	}

}
