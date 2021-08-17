package proj.unipe.controllers;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import proj.unipe.entities.Categoria;
import proj.unipe.services.CategoriaService;

@Controller
@RequestMapping(value = "categoria")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;

	@RequestMapping(method = RequestMethod.GET, value = { "listar" })
	public String list(ModelMap map) {

		List<Categoria> categorias = categoriaService.listar();
		map.addAttribute("categorias", categorias);
		map.addAttribute("filtro", new Categoria());
		return "/categoria/listar";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "filtrar" })
	public String filter(@ModelAttribute("filtro") Categoria filtro, ModelMap map) {
		try {
			if (filtro.getNome() == null || "".equals(filtro.getNome())) {
				return "redirect:/categoria/listar";
			}

			List<Categoria> categorias = categoriaService.buscaPorNome(filtro.getNome());
			map.addAttribute("categorias", categorias);
			map.addAttribute("filtro", filtro);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/categoria/listar";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "novo" })
	public String insertForm(ModelMap map) {
		Categoria categoria = new Categoria();
		map.addAttribute("categoria", categoria);
		map.addAttribute("selectDeStatus", selectDeStatus());
		return "/categoria/novo";
	}

	public List<String> selectDeStatus() {

		List<String> lista = new ArrayList<String>();
		lista.add("Selecione");
		lista.add("Ativo");
		lista.add("Não-Ativo");

		return lista;
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/{id}/updateForm" })
	public String updateForm(@PathVariable("id") Long id, ModelMap map) {
		Categoria categoria = categoriaService.buscarPorId(id);
		map.addAttribute("categoria", categoria);
		map.addAttribute("selectDeStatus", selectDeStatus());
		return "/categoria/novo";
	}

}
