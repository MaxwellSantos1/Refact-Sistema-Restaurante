package proj.unipe.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Pedido extends AbstractEntity {

	@Id @GeneratedValue
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date data;

	@Column(length = 20)
	private String status;
	
	
	private float total;

	@ManyToOne
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;

	
	@OneToMany(fetch =FetchType.EAGER, mappedBy = "pedido", cascade = CascadeType.PERSIST) List<ItemCardapio> itemCardapio;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<ItemCardapio> getItemCardapio() {
		return itemCardapio;
	}

	public void setItemCardapio(List<ItemCardapio> itemCardapio) {
		this.itemCardapio = itemCardapio;
	}

	/**
	 * @deprecated Use {@link proj.unipe.entities.Funcionario#getTotal(proj.unipe.entities.Pedido)} instead
	 */
	public float getTotal() {
		return funcionario.getTotal(this);
	}

	public List<Pedido> buscarPedido(EntityManager manager) {
		String sql = "select p from Pedido p where 1=1";
		if (getId() != null && getId().equals("")) {
			sql += "and p.id =" + getId();
		}
		if (getStatus() != null) {
			sql += "and p.status =" + getStatus();
		}
		Query query = manager.createQuery(sql);
		return query.getResultList();
	}

}
