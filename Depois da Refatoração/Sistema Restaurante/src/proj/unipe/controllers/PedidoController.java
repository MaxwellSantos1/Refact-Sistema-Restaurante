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
import proj.unipe.entities.Mesa;
import proj.unipe.entities.Pedido;
import proj.unipe.entities.Tradicional;
import proj.unipe.services.CardapioService;
import proj.unipe.services.MesaService;

@Controller
@RequestMapping(value = "pedido")
public class PedidoController {

	private PedidoControllerProduct pedidoControllerProduct = new PedidoControllerProduct();

	@Autowired
	private CardapioService cardapioService;

	@Autowired
	private MesaService mesaService;
	
	public static int idItem = 0;

	@RequestMapping(method = RequestMethod.GET, value = { "listar" })
	public String list(ModelMap map) {

		return pedidoControllerProduct.list(map);

	}

	@RequestMapping(method = RequestMethod.GET, value = { "novo" })
	public String novoPedido(ModelMap map, HttpSession sessao) {

		sessao.setAttribute("carrinho", null);
		idItem = 0;
		
		ItemCardapio item = item();
		map.addAttribute("item", item);
		map.addAttribute("selectMesas", selectMesas());
		map.addAttribute("selectStatus", pedidoControllerProduct.selectStatus());
		map.addAttribute("selectCardapio", selectCardapio());

		return "/pedido/novo";
	}

	private ItemCardapio item() {
		ItemCardapio item = new ItemCardapio();
		Tradicional tradicional = new Tradicional();
		tradicional.setMesa(new Mesa());
		item.setTradicional(tradicional);
		item.setCardapio(new Cardapio());
		return item;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}/removerItem")
	public String remove(@ModelAttribute("item") ItemCardapio itemCardapio, @PathVariable("id") Long id, HttpSession sessao, ModelMap map){
		
		List<ItemCardapio> carrinho = extracted(sessao);
		
		carrinho.remove(idItem);

		--idItem;
		
		
		map.addAttribute("carrinho", carrinho);
		map.addAttribute("selectMesas", selectMesas());
		map.addAttribute("selectStatus", pedidoControllerProduct.selectStatus());
		map.addAttribute("selectCardapio", selectCardapio());
		
		return "/pedido/novo";
	}

	private List<ItemCardapio> extracted(HttpSession sessao) {
		return (List<ItemCardapio>) sessao.getAttribute("carrinho");
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/adicionarItem" })
	public String adicionarItem(@ModelAttribute("item") ItemCardapio itemCardapio, HttpSession sessao, ModelMap map) {

		List<ItemCardapio> carrinho = carrinho(sessao);
		itemCardapio.itemCardapio(cardapioService);
		if (sessao.getAttribute("carrinho") == null) {
			
			carrinho.add(itemCardapio);
			sessao.setAttribute("carrinho", carrinho);
			sessao.setAttribute("pedido", itemCardapio.getPedido());
			sessao.setAttribute("id", idItem);
			

		} else {

			carrinho.add(itemCardapio);
			sessao.setAttribute("carrinho", carrinho);
			sessao.setAttribute("id",idItem++);
			
		}
		
		map.addAttribute("carrinho", carrinho);
		map.addAttribute("selectMesas", selectMesas());
		map.addAttribute("selectStatus", pedidoControllerProduct.selectStatus());
		map.addAttribute("selectCardapio", selectCardapio());
		

		return "/pedido/novo";
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

	@RequestMapping(method = RequestMethod.GET, value = { "finalizarPedido" })
	public String finalizarPedido( HttpSession sessao, ModelMap map) {
		
		return pedidoControllerProduct.finalizarPedido(sessao, map);
	}
	
	public Map<Long, String> selectMesas() {
		List<Mesa> mesas = mesaService.listar();
		Map<Long, String> mapa = new HashMap<Long, String>();
		mapa.put(0L, "selecione");
		for (Mesa mesa : mesas) {

			mapa.put(mesa.getId(), mesa.getNumero());
		}

		return mapa;
	}
	

	public List<String> selectStatus() {

		return pedidoControllerProduct.selectStatus();
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
		return pedidoControllerProduct.filtrar(filtro, map);

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}/detalharPedido")
	public String detalharPedido(@PathVariable("id") Long id, ModelMap map) {
		
		
		
		//apos ter o tradicional que possui uma determinada mesa eu busco o objeto mesa daquele pedido tradicional
		//Mesa mesa = mesaService.buscarPorId(tradicional.getMesa().getId());
		
		//agora seto o nome da mesa que corresponde ao pedido
		//tradicional.setMesa(mesa);
		
		return pedidoControllerProduct.detalharPedido(id, map);
	}

}
