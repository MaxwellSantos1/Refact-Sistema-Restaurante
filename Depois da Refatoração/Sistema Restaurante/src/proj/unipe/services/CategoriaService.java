package proj.unipe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import proj.unipe.daos.CategoriaDAO;
import proj.unipe.entities.Categoria;

@Service
@Transactional
public class CategoriaService {

	@Autowired
	private CategoriaDAO categoriaDAO;

	public void inserir(Categoria categoria) throws Exception {

		categoriaDAO.inserir(categoria);

	}

	public void atualizar(Categoria categoria) throws Exception {

		categoriaDAO.atualizar(categoria);

	}

	public void excluir(Categoria categoria) {

		categoriaDAO.excluir(categoria);

	}

	public Categoria buscarPorId(Long id) {

		Categoria categoria = null;

		return categoria = (Categoria) categoriaDAO.buscarPorID(id);

	}

	// método para realizar a busca por nome da categoria com CRITERIA
	public List<Categoria> buscaPorNome(String nome) {

		List<Categoria> categorias = null;

		return categorias = categoriaDAO.buscaPorNome(nome);

	}

	public List<Categoria> listar() {

		List<Categoria> categorias = null;

		return categorias = categoriaDAO.listar();

	}
	

	public List<Categoria> buscarCategoriasAtivas(){
		
		return categoriaDAO.buscarCategoriasAtivas();
		
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/{id}/ativar" })
	public String ativar(@PathVariable("id") Long id, ModelMap map) {
		Categoria categoria = buscarPorId(id);
		categoria.setStatus("Ativo");
		try {
			atualizar(categoria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/categoria/listar";
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/{id}/desativar" })
	public String desativar(@PathVariable("id") Long id, ModelMap map) {
		Categoria categoria = buscarPorId(id);
		categoria.setStatus("Não-Ativo");
		try {
			atualizar(categoria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/categoria/listar";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}/remove")
	public String remove(@PathVariable("id") Long id) {
		Categoria categoria = buscarPorId(id);
		if (categoria != null) {
			try {
				excluir(categoria);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:/categoria/listar";
	}

}