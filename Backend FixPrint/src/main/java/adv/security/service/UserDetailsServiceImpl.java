package adv.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import adv.model.entities.Usuario;
import adv.repository.UsuarioRepository;
import adv.security.entity.UsuarioPrincipal;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByUsername(username).get();
		return UsuarioPrincipal.build(usuario) ;
	}

}
