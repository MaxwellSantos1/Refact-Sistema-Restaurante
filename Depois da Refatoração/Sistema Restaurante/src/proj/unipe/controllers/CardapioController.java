package proj.unipe.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import proj.unipe.entities.Cardapio;
import proj.unipe.entities.Categoria;
import proj.unipe.services.CardapioControllerProduct;
import proj.unipe.services.CategoriaService;

@Controller
@RequestMapping(value = "cardapio")
public class CardapioController {

	private CardapioControllerProduct cardapioControllerProduct = new CardapioControllerProduct();

	@Autowired
	private CategoriaService categoriaService;

	@RequestMapping(method = RequestMethod.GET, value = { "listar" })
	public String list(ModelMap map) {

		return cardapioControllerProduct.list(map, this);

	}

	public Map<Long, String> selectDeCategoria() {

		List<Categoria> categoriasAtivas = categoriaService.buscarCategoriasAtivas();

		Map<Long, String> mapa = new HashMap<Long, String>();
		mapa.put(0L, "selecione");
		for (Categoria categoria : categoriasAtivas) {

			mapa.put(categoria.getId(), categoria.getNome());
		}

		return mapa;
	}

	public List<String> selectDeStatus() {

		return cardapioControllerProduct.selectDeStatus();
	}

	@RequestMapping(method = RequestMethod.GET, value = { "filtrar" })
	public String filtrar(@ModelAttribute("filtro") Cardapio filtro, ModelMap map) {

		return cardapioControllerProduct.filtrar(filtro, map, this);

	}

	@RequestMapping(method = RequestMethod.GET, value = { "novo" })
	public String insertForm(ModelMap map) {
		return cardapioControllerProduct.insertForm(map, this);
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(method = RequestMethod.GET, value = { "salvar" })
	public String save(@ModelAttribute("cardapio") @Valid Cardapio cardapio, BindingResult result, ModelMap map) {
		return cardapioControllerProduct.save(cardapio, result, map, this);
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/{id}/updateForm" })
	public String updateForm(@PathVariable("id") Long id, ModelMap map) {
		return cardapioControllerProduct.updateForm(id, map, this);
	}

}
