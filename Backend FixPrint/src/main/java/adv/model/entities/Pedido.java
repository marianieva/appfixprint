package adv.model.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import adv.model.enums.StatusPedido;
import adv.model.enums.TipoPedido;


/**
 * The persistent class for the pedidos database table.
 * 
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="pedidos")
@NamedQuery(name="Pedido.findAll", query="SELECT p FROM Pedido p")
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pedido")
	private int idPedido;

	@Enumerated(value = EnumType.STRING)
	private StatusPedido estado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_pedido")
	private Date fechaPedido;

	@Column(name="tipo_pedido")
	@Enumerated(value = EnumType.STRING)
	private TipoPedido tipoPedido;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detallePedidos = new ArrayList<>();

	
	public void addDetallePedido(DetallePedido detallePedido) {
		this.detallePedidos.add(detallePedido);
        detallePedido.setPedido(this);
	
	}

}