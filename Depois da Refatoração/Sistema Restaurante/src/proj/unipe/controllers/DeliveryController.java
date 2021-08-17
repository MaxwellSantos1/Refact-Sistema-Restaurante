package proj.unipe.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import proj.unipe.entities.Cardapio;
import proj.unipe.entities.ItemCardapio;
import proj.unipe.entities.Pedido;
import proj.unipe.services.CardapioService;
import proj.unipe.services.ClienteService;

@Controller
@RequestMapping(value = "delivery")
public class DeliveryController {

	private DeliveryControllerProduct deliveryControllerProduct = new DeliveryControllerProduct();

	@Autowired
	private CardapioService cardapioService;

	@Autowired
	private ClienteService clienteService;

	public static int idItem = 0;

	@RequestMapping(method = RequestMethod.GET, value = { "listar" })
	public String list(ModelMap map) {

		return deliveryControllerProduct.list(map);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}/removerItem")
	public String remove(@ModelAttribute("item") ItemCardapio itemCardapio, @PathVariable("id") Long id,
			HttpSession sessao, ModelMap map) {

		return deliveryControllerProduct.remove(itemCardapio, id, sessao, map, this);
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/adicionarItem" })
	public String adicionarItem(@ModelAttribute("item") ItemCardapio itemCardapio, HttpSession sessao, ModelMap map) {

		List<ItemCardapio> carrinho = carrinho(sessao);
		itemCardapio.itemCardapio(cardapioService);
		if (sessao.getAttribute("carrinho") == null) {

			carrinho.add(itemCardapio);
			sessao.setAttribute("carrinho", carrinho);
			sessao.setAttribute("delivery", itemCardapio.getDelivery());
			sessao.setAttribute("id", idItem);

		} else {

			carrinho.add(itemCardapio);
			sessao.setAttribute("carrinho", carrinho);
			sessao.setAttribute("id", idItem++);

		}

		map.addAttribute("carrinho", carrinho);
		map.addAttribute("selectStatus", deliveryControllerProduct.selectStatus());
		map.addAttribute("selectCardapio", selectCardapio());

		return "/delivery/novo";
	}

	private List<ItemCardapio> carrinho(HttpSession sessao) {
		List<ItemCardapio> carrinho = null;
		if (sessao.getAttribute("carrinho") == null) {
			carrinho = new ArrayList<ItemCardapio>();
		} else {
			carrinho = extracted(sessao);
		}
		return carrinho;
	}

	private List<ItemCardapio> extracted(HttpSession sessao) {
		return (List<ItemCardapio>) sessao.getAttribute("carrinho");
	}

	@RequestMapping(method = RequestMethod.GET, value = { "finalizarPedido" })
	public String finalizarPedido(HttpSession sessao, ModelMap map) {

		return deliveryControllerProduct.finalizarPedido(sessao, map);

	}

	public List<String> selectStatus() {

		return deliveryControllerProduct.selectStatus();
	}

	public Map<Long, String> selectCardapio() {
		List<Cardapio> itens = cardapioService.listar();
		Map<Long, String> mapa = new HashMap<Long, String>();
		mapa.put(0L, "selecione");
		for (Cardapio cardapio : itens) {

			mapa.put(cardapio.getId(), cardapio.getNome());
		}

		return mapa;
	}

	@RequestMapping(method = RequestMethod.GET, value = { "filtrar" })
	public String filtrar(@ModelAttribute("filtro") Pedido filtro, ModelMap map) {
		return deliveryControllerProduct.filtrar(filtro, map);

	}

	@RequestMapping(method = RequestMethod.GET, value = { "novoDelivery" })
	public String novoDelivery(ModelMap map, HttpSession sessao) {

		return deliveryControllerProduct.novoDelivery(map, sessao, this);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}/detalharDelivery")
	public String detalharPedido(@PathVariable("id") Long id, ModelMap map) {

		return deliveryControllerProduct.detalharPedido(id, map);
	}

}
