package adv.model.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import adv.model.enums.StatusIncidencia;


/**
 * The persistent class for the incidencias database table.
 * 
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name="incidencias")
@NamedQuery(name="Incidencia.findAll", query="SELECT i FROM Incidencia i")
public class Incidencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_incidencia")
	private int idIncidencia;

	@Column(name="comentario_tecnico")
	private String comentarioTecnico;

	@Column(name="descripcion_cliente")
	private String descripcionCliente;
	
	@Enumerated(value = EnumType.STRING)
	private StatusIncidencia estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_fin")
	private Date fechaFin;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_inicio")
	private Date fechaInicio;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_alta")
	private Date fechaAlta;

	//bi-directional many-to-one association to Impresora
	@ManyToOne
	@JoinColumn(name="serial_number")
	private Impresora serialNumber;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_tecnico")
	private Usuario tecnico;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "incidencia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductoEnIncidencia> productosEnIncidencia = new ArrayList<>();
	
	public Incidencia(int idIncidencia, String comentarioTecnico, String descripcionCliente, StatusIncidencia estado,
			Date fechaFin, Date fechaInicio, Impresora serialNumber, Usuario tecnico) {
		super();
		this.idIncidencia = idIncidencia;
		this.comentarioTecnico = comentarioTecnico;
		this.descripcionCliente = descripcionCliente;
		this.estado = estado;
		this.fechaFin = fechaFin;
		this.fechaInicio = fechaInicio;
		this.serialNumber = serialNumber;
		this.tecnico = tecnico;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Incidencia))
			return false;
		Incidencia other = (Incidencia) obj;
		if( idIncidencia == other.idIncidencia)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idIncidencia;
		return result;
	}

	

}