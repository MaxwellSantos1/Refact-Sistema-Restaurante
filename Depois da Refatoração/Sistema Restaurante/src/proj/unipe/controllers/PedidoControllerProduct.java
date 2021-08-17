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

import proj.unipe.entities.ItemCardapio;
import proj.unipe.entities.Pedido;
import proj.unipe.entities.Tradicional;
import proj.unipe.services.ItemCardapioService;
import proj.unipe.services.PedidoService;

public class PedidoControllerProduct  {
	private PedidoService pedidoService;
	private ItemCardapioService itemCardapioService;

	@RequestMapping(method = RequestMethod.GET, value = { "listar" })
	public String list(ModelMap map) {
		List<Tradicional> pedidos = pedidoService.listarTradicional();
		map.addAttribute("pedidos", pedidos);
		map.addAttribute("selectStatus", selectStatus());
		map.addAttribute("filtro", new Pedido());
		return "/pedido/listar";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "finalizarPedido" })
	public String finalizarPedido(HttpSession sessao, ModelMap map) {
		ItemCardapio itemCardapio = itemCardapio(sessao);
		Pedido pedido = (Pedido) sessao.getAttribute("pedido");
		pedidoService.inserir(pedido);
		List<Tradicional> pedidos = pedidoService.listarTradicional();
		map.addAttribute("pedidos", pedidos);
		map.addAttribute("selectStatus", selectStatus());
		map.addAttribute("filtro", new Pedido());
		return "/pedido/listar";
	}

	public ItemCardapio itemCardapio(HttpSession sessao) {
		Pedido pedido = (Pedido) sessao.getAttribute("pedido");
		pedido.setData(new Date());
		return itemCardapio(null);
	}

	private ItemCardapio itemcardapio(List<ItemCardapio> carrinho, Pedido pedido) {
		for (ItemCardapio itemCardapio : carrinho) {
			itemCardapio.setPedido(pedido);
		}
		pedido.setItemCardapio(carrinho);
		return itemCardapio(null);
	}

	@RequestMapping(method = RequestMethod.GET, value = { "filtrar" })
	public String filtrar(@ModelAttribute("filtro") Pedido filtro, ModelMap map) {
		try {
			List<Pedido> pedidos = pedidoService.buscaPorNumeroPedido(filtro.getId());
			map.addAttribute("pedidos", pedidos);
			map.addAttribute("selectStatus", selectStatus());
			map.addAttribute("filtro", filtro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/pedido/listar";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}/detalharPedido")
	public String detalharPedido(@PathVariable("id") Long id, ModelMap map) {
		Tradicional tradicional = (Tradicional) pedidoService.buscarPorID(id);
		List<ItemCardapio> itensDoPedido = itemCardapioService.buscarItensPorIdDoPedido(id);
		map.addAttribute("tradicional", tradicional);
		map.addAttribute("itensDoPedido", itensDoPedido);
		return "pedido/detalhar";
	}

	public List<String> selectStatus() {
		List<String> status = new ArrayList<String>();
		status.add("pendente");
		status.add("atendido");
		status.add("cancelado");
		return status;
	}
}