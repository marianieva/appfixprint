package adv.security.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import adv.model.entities.Rol;
import adv.model.entities.Usuario;
import adv.model.enums.RolNombre;
import adv.security.dto.JwtDto;
import adv.security.dto.LoginUsuario;
import adv.security.dto.Mensaje;
import adv.security.dto.NuevoUsuario;
import adv.security.jwt.JwtProvider;
import adv.service.RolService;
import adv.service.UsuarioService;
import adv.service.ZonaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	RolService rolService;
	
	@Autowired
	ZonaService zonaService;

	@Autowired
	JwtProvider jwtProvider;

	@PostMapping("/nuevo")
    public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>(new Mensaje("Error en los campos"), HttpStatus.BAD_REQUEST);
        if (usuarioService.existsByUsername(nuevoUsuario.getUsername()))
            return new ResponseEntity<>(new Mensaje("El nombre de usuario ya existe"), HttpStatus.BAD_REQUEST);

        Usuario usuario = new Usuario(nuevoUsuario.getUsername(),
        		nuevoUsuario.getNombre(),
                passwordEncoder.encode(nuevoUsuario.getPassword()),
                nuevoUsuario.getApellidos(),
        		nuevoUsuario.getDireccion());

        Set<Rol> roles = new HashSet<>();
        
        if (nuevoUsuario.getRol().contains("ROL_TECNICO"))
            roles.add(rolService.getByRolNombre(RolNombre.ROL_TECNICO).get());
        else if (nuevoUsuario.getRol().contains("ROL_CLIENTE"))
            roles.add(rolService.getByRolNombre(RolNombre.ROL_CLIENTE).get());
        else if (nuevoUsuario.getRol().contains("ROL_ADMIN"))
            roles.add(rolService.getByRolNombre(RolNombre.ROL_ADMIN).get());

        usuario.setRoles(roles);
        
        usuario.setZona(zonaService.findOne(nuevoUsuario.getZona()));
        
        usuarioService.altaUsuario(usuario);

        return new ResponseEntity<>(new Mensaje("Usuario guardado"), HttpStatus.CREATED);
    }


	@PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("campos mal puestos"), HttpStatus.BAD_REQUEST);
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getUsername(), loginUsuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        JwtDto jwtDto = new JwtDto(jwt);
        
        Optional<Usuario> usuarioOptional = usuarioService.getByUsername(loginUsuario.getUsername());
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            List<RolNombre> roles = usuario.getRoles()
                                        .stream()
                                        .map(Rol::getRolNombre)
                                        .collect(Collectors.toList());
            
            jwtDto.setUsername(usuario.getIdUsuario());
            jwtDto.setRoles(roles);
        } else {
        	return new ResponseEntity(jwtDto, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }

}
