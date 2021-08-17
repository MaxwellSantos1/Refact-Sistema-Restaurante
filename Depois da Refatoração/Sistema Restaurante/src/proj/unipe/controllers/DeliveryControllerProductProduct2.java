package proj.unipe.controllers;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import proj.unipe.entities.Cliente;
import proj.unipe.entities.Delivery;
import proj.unipe.entities.ItemCardapio;
import proj.unipe.entities.Pedido;
import proj.unipe.entities.Usuario;
import proj.unipe.services.PedidoService;

public class DeliveryControllerProductProduct2 {
	private DeliveryControllerProductProduct deliveryControllerProductProduct = new DeliveryControllerProductProduct();
	private PedidoService pedidoService;

	public DeliveryControllerProductProduct getDeliveryControllerProductProduct() {
		return deliveryControllerProductProduct;
	}

	public PedidoService getPedidoService() {
		return pedidoService;
	}

	@RequestMapping(method = RequestMethod.GET, value = { "listar" })
	public String list(ModelMap map) {
		List<Delivery> pedidos = pedidoService.listarDelivery();
		map.addAttribute("pedidos", pedidos);
		map.addAttribute("selectStatus", deliveryControllerProductProduct.selectStatus());
		map.addAttribute("filtro", new Pedido());
		return "/delivery/listar";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "filtrar" })
	public String filtrar(@ModelAttribute("filtro") Pedido filtro, ModelMap map) {
		try {
			List<Pedido> pedidos = pedidoService.buscarPedido(filtro);
			map.addAttribute("pedidos", pedidos);
			map.addAttribute("selectStatus", deliveryControllerProductProduct.selectStatus());
			map.addAttribute("filtro", filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/delivery/listar";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "finalizarPedido" })
	public String finalizarPedido(HttpSession sessao, ModelMap map) {
		Delivery delivery = delivery(sessao);
		@SuppressWarnings("unchecked")
		List<ItemCardapio> carrinho = (List<ItemCardapio>) sessao.getAttribute("carrinho");
		Cliente cliente = cliente(sessao);
		Usuario usuario = (Usuario) sessao.getAttribute("usuario");
		for (ItemCardapio itemCardapio : carrinho) {
			itemCardapio.setDelivery(delivery);
			if (usuario instanceof Cliente) {
				itemCardapio.getDelivery().setCliente(cliente);
			}
		}
		pedidoService.inserir(delivery);
		List<Delivery> pedidos = pedidoService.listarDelivery();
		map.addAttribute("pedidos", pedidos);
		map.addAttribute("selectStatus", deliveryControllerProductProduct.selectStatus());
		map.addAttribute("filtro", new Pedido());
		return "/delivery/listar";
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
	 * @deprecated Use {@link proj.unipe.controllers.DeliveryControllerProductProduct#entrega(HttpSession)} instead
	 */
	private Delivery delivery(HttpSession sessao) {
		return deliveryControllerProductProduct.entrega(sessao);
	}
}