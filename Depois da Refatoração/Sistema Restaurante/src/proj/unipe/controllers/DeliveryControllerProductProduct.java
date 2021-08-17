package proj.unipe.controllers;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import proj.unipe.entities.Cardapio;
import proj.unipe.entities.Cliente;
import proj.unipe.entities.Delivery;
import proj.unipe.entities.ItemCardapio;
import proj.unipe.entities.Usuario;

public class DeliveryControllerProductProduct {
	@RequestMapping(method = RequestMethod.GET, value = "/{id}/removerItem")
	public String remove(@ModelAttribute("item") ItemCardapio itemCardapio, @PathVariable("id") Long id,
			HttpSession sessao, ModelMap map, DeliveryController deliveryController) {
		List<ItemCardapio> carrinho = (List<ItemCardapio>) sessao.getAttribute("carrinho");
		carrinho.remove(DeliveryController.idItem);
		--DeliveryController.idItem;
		map.addAttribute("carrinho", carrinho);
		map.addAttribute("selectStatus", selectStatus());
		map.addAttribute("selectCardapio", deliveryController.selectCardapio());
		return "/delivery/novo";
	}

	public List<String> selectStatus() {
		List<String> status = new ArrayList<String>();
		status.add("pendente");
		status.add("atendido");
		status.add("cancelado");
		return status;
	}

	@RequestMapping(method = RequestMethod.GET, value = { "novoDelivery" })
	public String novoDelivery(ModelMap map, HttpSession sessao, DeliveryController deliveryController) {
		ItemCardapio item = item(sessao);
		sessao.setAttribute("carrinho", null);
		map.addAttribute("item", item);
		map.addAttribute("selectStatus", selectStatus());
		map.addAttribute("selectCardapio", deliveryController.selectCardapio());
		return "/delivery/novo";
	}

	private ItemCardapio item(HttpSession sessao) {
		Delivery delivery = delivery1(sessao);
		ItemCardapio item = new ItemCardapio();
		item.setDelivery(delivery);
		item.setCardapio(new Cardapio());
		return item;
	}

	Delivery delivery1(HttpSession sessao) {
		Cliente cliente = cliente(sessao);
		Delivery delivery = new Delivery();
		delivery.setCliente(cliente);
		return delivery;
	}

	private Cliente cliente(HttpSession sessao) {
		Cliente cliente = new Cliente();
		Usuario usuario = (Usuario) sessao.getAttribute("usuario");
		if (usuario instanceof Cliente) {
			cliente = (Cliente) usuario;
		}
		return cliente;
	}

	/**
	 * @deprecated Use {@link #entrega(HttpSession)} instead
	 */
	Delivery delivery(HttpSession sessao) {
		return entrega(sessao);
	}

	Delivery entrega(HttpSession sessao) {
		@SuppressWarnings("unchecked")
		List<ItemCardapio> carrinho = (List<ItemCardapio>) sessao.getAttribute("carrinho");
		Delivery delivery = (Delivery) sessao.getAttribute("delivery");
		delivery.setData(new Date());
		delivery.setItemCardapio(carrinho);
		return delivery;
	}
}