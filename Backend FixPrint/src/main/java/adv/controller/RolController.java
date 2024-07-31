package adv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import adv.model.entities.Rol;
import adv.service.RolService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/roles")
public class RolController {
	
	@Autowired
	private RolService rolService;
	
	@GetMapping("/lista")
	public List<Rol> todosLosRoles(){
		return rolService.findAllRole();
	}

}
