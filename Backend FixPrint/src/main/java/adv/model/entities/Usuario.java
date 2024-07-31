package adv.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The persistent class for the usuarios database table.
 * 
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="usuarios")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private int idUsuario;
	
	@Column(unique = true)
	private String username;

	private String apellidos;

	private String direccion;

	private String nombre;

	private String password;

	@ManyToOne
	@JoinColumn(name="id_zona")
	private Zona zona;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "usuario_rol", 
			joinColumns = @JoinColumn(name = "id_usuario"),
			inverseJoinColumns = @JoinColumn(name="id_rol"))
	private Set<Rol> roles = new HashSet<>();
	
	@JsonManagedReference
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AlmacenUsuario> productoEnAlmacen = new ArrayList<>();
	
	
	
	public Usuario(String username, String nombre, String password, String apellidos, String direccion) {
		super();
		this.username = username;
		this.nombre = nombre;
		this.password = password;
		this.apellidos = apellidos;
		this.direccion = direccion;
	}
	
	
	
	
	/*
	 * @Override public boolean equals(Object obj) { if (this == obj) return true;
	 * if (!(obj instanceof Usuario)) return false; Usuario other = (Usuario) obj;
	 * return idUsuario == other.idUsuario && Objects.equals(productos,
	 * other.productos) && Objects.equals(roles, other.roles); }
	 * 
	 * @Override public int hashCode() { return Objects.hash(idUsuario, productos,
	 * roles); }
	 * 
	 * public void addProducto(Producto producto) { if(this.productos == null) {
	 * this.productos = new ArrayList<>(); } this.productos.add(producto); }
	 */
	
	public void addRol(Rol rol) {
		if(this.roles == null) {
			this.roles = new HashSet<>();
		}
		this.roles.add(rol);
	}


	public Usuario(int idUsuario, String username, String apellidos, String direccion, String nombre) {
		super();
		this.idUsuario = idUsuario;
		this.username = username;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.nombre = nombre;
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Usuario))
			return false;
		Usuario other = (Usuario) obj;
		return idUsuario == other.idUsuario;
	}




	@Override
	public int hashCode() {
		return Objects.hash(idUsuario);
	}
	
	

}