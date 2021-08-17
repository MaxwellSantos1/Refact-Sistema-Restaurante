package proj.unipe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import proj.unipe.controllers.CardapioController;
import proj.unipe.daos.CardapioDAO;
import proj.unipe.daos.CategoriaDAO;
import proj.unipe.entities.Cardapio;
import proj.unipe.entities.Categoria;

@Service
@Transactional
public class CardapioService {

	@Autowired
	private CardapioDAO cardapioDAO;
	@Autowired
	private CategoriaDAO categoriaDAO;

	public void inserir(Cardapio cardapio) {

		cardapioDAO.inserir(cardapio);

	}

	public void atualizar(Cardapio cardapio) {

		cardapioDAO.atualizar(cardapio);
	}

	public void excluir(Cardapio cardapio) {

		cardapioDAO.excluir(cardapio);

	}

	// método para realizar a busca por numero da mesa com CRITERIA
	public List<Cardapio> buscaPorNome(String nome) {

		List<Cardapio> cardapios = null;

		return cardapios = cardapioDAO.buscarPorNome(nome);

	}

	public Cardapio buscarPorId(Long id) {

		Cardapio cardapio = null;

		return cardapio = (Cardapio) cardapioDAO.buscarPorID(id);

	}

	public List<Cardapio> listar(){

		List<Cardapio> cardapio = null;

		return cardapio = cardapioDAO.listar();

	}
	
	public List<Cardapio> buscarCardapioPorCategoria(Cardapio filtro){
		
		return cardapioDAO.buscarCardapioPorCategoria(filtro);
		
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/{id}/ativar" })
	public String ativar(@PathVariable("id") Long id, ModelMap map) {
		Cardapio cardapio = buscarPorId(id);
		cardapio.setStatus("Ativo");
		try {
			atualizar(cardapio);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/cardapio/listar";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/{id}/desativar" })
	public String desativar(@PathVariable("id") Long id, ModelMap map) {
		Cardapio cardapio = buscarPorId(id);
		cardapio.setStatus("Não-Ativo");
		try {
			atualizar(cardapio);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/cardapio/listar";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}/remove")
	public String remove(@PathVariable("id") Long id) {
		Cardapio cardapio = buscarPorId(id);
		if (cardapio != null) {
			try {
				excluir(cardapio);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:/cardapio/listar";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "filtrar" })
	public String filtrar(@ModelAttribute("filtro") Cardapio filtro, ModelMap map,
			CardapioController cardapioController) {
		try {
			if (filtro.getNome() == null && "".equals(filtro.getNome())) {
			} else {
				List<Cardapio> cardapios = buscaPorNome(filtro.getNome());
				map.addAttribute("cardapios", cardapios);
				map.addAttribute("selectDeCategoria", cardapioController.selectDeCategoria());
				map.addAttribute("filtro", filtro);
			}
			Categoria r = filtro.getCategoria();
			if (!(r.getId() != null && r.getId() != 0)) {
			} else {
				List<Cardapio> cardapios = buscarCardapioPorCategoria(filtro);
				map.addAttribute("cardapios", cardapios);
				map.addAttribute("selectDeCategoria", cardapioController.selectDeCategoria());
				map.addAttribute("filtro", filtro);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/cardapio/listar";
	}
	

}
