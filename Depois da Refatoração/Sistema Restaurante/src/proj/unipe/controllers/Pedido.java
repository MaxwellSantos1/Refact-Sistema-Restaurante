package proj.unipe.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;


public interface Pedido {

	String list(ModelMap map);

	String finalizarPedido(HttpSession sessao, ModelMap map);

	String filtrar(Pedido filtro, ModelMap map);

	String detalharPedido(Long id, ModelMap map);

	List<String> selectStatus();

}