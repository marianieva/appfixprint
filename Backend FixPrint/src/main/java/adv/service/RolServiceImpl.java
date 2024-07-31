package adv.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import adv.model.entities.Rol;
import adv.model.entities.Usuario;
import adv.model.enums.RolNombre;
import adv.repository.RolRepository;

@Service
public class RolServiceImpl implements RolService{
	
	@Autowired
	private RolRepository rolRepository;

	@Override
	public Rol dimeElRol(Usuario usuario) {
		return null;
	}

	@Override
	public Optional<Rol> getByRolNombre(RolNombre rolNombre) {
		return rolRepository.findByRolNombre(rolNombre);
	}

	@Override
	public List<Rol> findAllRole() {
		return rolRepository.findAll();
	}

}
