package proj.unipe.controllers;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import proj.unipe.entities.Delivery;
import proj.unipe.entities.ItemCardapio;
import proj.unipe.entities.Pedido;
import proj.unipe.services.ItemCardapioService;

public class DeliveryControllerProduct {
	private DeliveryControllerProductProduct2 deliveryControllerProductProduct2 = new DeliveryControllerProductProduct2();
	private ItemCardapioService itemCardapioService;

	@RequestMapping(method = RequestMethod.GET, value = { "listar" })
	public String list(ModelMap map) {
		return deliveryControllerProductProduct2.list(map);
	}

	@RequestMapping(method = RequestMethod.GET, value = { "filtrar" })
	public String filtrar(@ModelAttribute("filtro") Pedido filtro, ModelMap map) {
		return deliveryControllerProductProduct2.filtrar(filtro, map);
	}

	@RequestMapping(method = RequestMethod.GET, value = { "finalizarPedido" })
	public String finalizarPedido(HttpSession sessao, ModelMap map) {
		return deliveryControllerProductProduct2.finalizarPedido(sessao, map);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}/detalharDelivery")
	public String detalharPedido(@PathVariable("id") Long id, ModelMap map) {
		Delivery delivery = (Delivery) deliveryControllerProductProduct2.getPedidoService().buscarPorID(id);
		List<ItemCardapio> itensDoPedido = itemCardapioService.buscarItensPorIdDoPedido(id);
		map.addAttribute("delivery", delivery);
		map.addAttribute("itensDoPedido", itensDoPedido);
		return "delivery/detalhar";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}/removerItem")
	public String remove(@ModelAttribute("item") ItemCardapio itemCardapio, @PathVariable("id") Long id,
			HttpSession sessao, ModelMap map, DeliveryController deliveryController) {
		return deliveryControllerProductProduct2.getDeliveryControllerProductProduct().remove(itemCardapio, id, sessao, map, deliveryController);
	}

	public List<String> selectStatus() {
		return deliveryControllerProductProduct2.getDeliveryControllerProductProduct().selectStatus();
	}

	@RequestMapping(method = RequestMethod.GET, value = { "novoDelivery" })
	public String novoDelivery(ModelMap map, HttpSession sessao, DeliveryController deliveryController) {
		return deliveryControllerProductProduct2.getDeliveryControllerProductProduct().novoDelivery(map, sessao, deliveryController);
	}
}