package proj.unipe.services;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import proj.unipe.controllers.CardapioController;
import proj.unipe.entities.Cardapio;
import proj.unipe.entities.Categoria;

public class CardapioControllerProduct {
	public CardapioService cardapioService;

	@RequestMapping(method = RequestMethod.GET, value = { "listar" })
	public String list(ModelMap map, CardapioController cardapioController) {
		List<Cardapio> cardapios = cardapioService.listar();
		map.addAttribute("cardapios", cardapios);
		map.addAttribute("selectDeCategoria", cardapioController.selectDeCategoria());
		map.addAttribute("filtro", new Cardapio());
		return "/cardapio/listar";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/{id}/updateForm" })
	public String updateForm(@PathVariable("id") Long id, ModelMap map, CardapioController cardapioController) {
		Cardapio cardapio = cardapioService.buscarPorId(id);
		map.addAttribute("cardapio", cardapio);
		map.addAttribute("selectDeCategoria", cardapioController.selectDeCategoria());
		map.addAttribute("selectDeStatus", selectDeStatus());
		return "/cardapio/novo";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "filtrar" })
	public String filtrar(@ModelAttribute("filtro") Cardapio filtro, ModelMap map,
			CardapioController cardapioController) {
		return cardapioService.filtrar(filtro, map, cardapioController);
	}

	/**
	 * @deprecated Use {@link proj.unipe.entities.Cardapio#save(proj.unipe.services.CardapioControllerProduct,BindingResult,ModelMap,CardapioController)} instead
	 */
	@RequestMapping(method = RequestMethod.GET, value = { "salvar" })
	public String save(@ModelAttribute("cardapio") @Valid Cardapio cardapio, BindingResult result, ModelMap map,
			CardapioController cardapioController) {
				return cardapio.save(this, result, map, cardapioController);
			}

	@RequestMapping(method = RequestMethod.GET, value = { "novo" })
	public String insertForm(ModelMap map, CardapioController cardapioController) {
		Cardapio cardapio = new Cardapio();
		cardapio.setCategoria(new Categoria());
		map.addAttribute("cardapio", cardapio);
		map.addAttribute("selectDeCategoria", cardapioController.selectDeCategoria());
		map.addAttribute("selectDeStatus", selectDeStatus());
		return "/cardapio/novo";
	}

	public List<String> selectDeStatus() {
		List<String> lista = new ArrayList<String>();
		lista.add("Selecione");
		lista.add("Ativo");
		lista.add("Não-Ativo");
		return lista;
	}
}