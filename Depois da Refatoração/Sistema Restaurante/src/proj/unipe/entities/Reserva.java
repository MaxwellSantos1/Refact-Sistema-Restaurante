package proj.unipe.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import proj.unipe.services.ReservaController;

@Entity
@Table(name ="reserva")
@SequenceGenerator(sequenceName = "reserva_seq", name = "reserva_id", allocationSize = 1)
public class Reserva extends AbstractEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reserva_id")
	private Long id;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dataInicial;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dataFinal;
	
	
	@NotNull(message="Campo obrigatório")
	@Column(length = 5)
	private int num_Pessoas;
	
	@NotEmpty(message="Campo obrigatório")
	@Column(length = 50)
	private String nome_responsavel;
	
	@ManyToOne
	@JoinColumn(name = "mesa_id")
	private Mesa mesa;
	
	@ManyToOne
	@JoinColumn(name = "funcionario_id")
	private Funcionario funcionario;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public int getNum_Pessoas() {
		return num_Pessoas;
	}

	public void setNum_Pessoas(int num_Pessoas) {
		this.num_Pessoas = num_Pessoas;
	}

	public String getNome_responsavel() {
		return nome_responsavel;
	}

	public void setNome_responsavel(String nome_responsavel) {
		this.nome_responsavel = nome_responsavel;
	}

	public Mesa getMesa() {
		return mesa;
	}

	public void setMesa(Mesa mesa) {
		this.mesa = mesa;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<Reserva> buscarReserva(EntityManager manager) {
		String sql = "select r from Reserva r where 1=1";
		if (getDataInicial() != null && getDataInicial().equals("")) {
			sql += "and r.dataInicial =" + getDataInicial();
		}
		if (getDataFinal() != null && getDataFinal().equals("")) {
			sql += "and r.dataFinal =" + getDataFinal();
		}
		if (getMesa().getId() != null && getMesa().getId().equals("")) {
			sql += "and r.mesa.id =" + getMesa().getId();
		}
		if (getNome_responsavel() != null && "".equals(getNome_responsavel())) {
			sql += "and r.nome_Responsavel =" + getNome_responsavel();
		}
		Query query = manager.createQuery(sql);
		return query.getResultList();
	}

	@RequestMapping(method = RequestMethod.GET, value = {"filtrar"})
	public String filter(ReservaController reservaController, ModelMap map) {
		try {
			if (getNome_responsavel() == null && "".equals(getNome_responsavel())) {
			} else {
				List<Reserva> reservas = reservaController.reservaService.buscarPorNomeResponsavel(getNome_responsavel());
	
				map.addAttribute("reservas", reservas);
				map.addAttribute("selectMesas", reservaController.reservaControllerProduct.selectMesas());
				map.addAttribute("filtro", this);
			}
			Mesa r = getMesa();
			if (!(r.getId() != null && r.getId() != 0)) {
			} else {
	
				List<Reserva> reservas = reservaController.reservaService.buscarPorMesa(this);
	
				map.addAttribute("reservas", reservas);
				map.addAttribute("selectMesas", reservaController.reservaControllerProduct.selectMesas());
				map.addAttribute("filtro", this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return "/reserva/listar";
	}
	
	
}
