package proj.unipe.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import proj.unipe.controllers.CardapioController;
import proj.unipe.services.CardapioControllerProduct;

@SequenceGenerator(sequenceName = "cardapio_seq", name = "cardapio_id", allocationSize = 1)
public class Cardapio extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cardapio_id")
	private Long id;
	
	@NotEmpty(message="O nome do cardápio é obrigatório")
	@Column(length = 30)
	private String nome;

	@NotNull(message="O preço é obrigatório")
	@Column(nullable = false, length = 30)
	private float preco;
	
	@Column( length = 30)
	private String status;

	@OneToMany(mappedBy = "cardapio")
	private List<ItemCardapio> itemCardapio;

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	public List<ItemCardapio> getItemCardapio() {
		return itemCardapio;
	}

	public void setItemCardapio(List<ItemCardapio> itemCardapio) {
		this.itemCardapio = itemCardapio;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@RequestMapping(method = RequestMethod.GET, value = { "salvar" })
	public String save(CardapioControllerProduct cardapioControllerProduct, BindingResult result, ModelMap map, CardapioController cardapioController) {
		if (result.hasErrors()) {
			map.addAttribute("cardapio", this);
			map.addAttribute("selectDeCategoria", cardapioController.selectDeCategoria());
			map.addAttribute("selectDeStatus", cardapioControllerProduct.selectDeStatus());
			return "/cardapio/novo";
		}
		try {
			Categoria r = getCategoria();
			if (getId() != null && getId() != 0 && !"Selecione".equals(getStatus())
					&& r.getId() != null && r.getId() != 0) {
				cardapioControllerProduct.cardapioService.atualizar(this);
			} else {
				Categoria r1 = getCategoria();
				if (!"Selecione".equals(getStatus()) && !(getId() != null && getId() != 0)
						&& r1.getId() != null && r1.getId() != 0) {
					cardapioControllerProduct.cardapioService.inserir(this);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/cardapio/listar";
	}

}
