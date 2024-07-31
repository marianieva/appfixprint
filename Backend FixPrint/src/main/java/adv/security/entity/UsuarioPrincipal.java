package adv.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import adv.model.entities.Usuario;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioPrincipal implements UserDetails{
	
	private String nombre;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	public UsuarioPrincipal(String nombre, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.nombre = nombre;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public static UsuarioPrincipal build(Usuario usuario) {
		List<GrantedAuthority> authorities =
				usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getRolNombre().name())).collect(Collectors.toList());
		return new UsuarioPrincipal(usuario.getNombre(), usuario.getUsername(), usuario.getPassword(), authorities);
		
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	
	

}
