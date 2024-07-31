package adv.service;

import java.util.List;

import adv.model.entities.Zona;

public interface ZonaService {
	
	Zona findOne(int idZona);
	List<Zona> findAllZona();

}
