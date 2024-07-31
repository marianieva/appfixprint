package adv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import adv.model.entities.Zona;
import adv.service.ZonaService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/zona")
public class ZonaController {

	@Autowired
    private ZonaService zonaService;
	
	 @GetMapping("/lista")
	    public List<Zona> todasLasZOnas() {
	        return zonaService.findAllZona();
	    }

}
