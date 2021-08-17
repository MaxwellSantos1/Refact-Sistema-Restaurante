package proj.unipe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import proj.unipe.daos.FuncionarioDAO;
import proj.unipe.entities.Funcionario;

@Service
@Transactional
public class FuncionarioService {

	@Autowired
	private FuncionarioDAO funcionarioDAO;

	public void inserir(Funcionario funcionario) {

		funcionarioDAO.inserir(funcionario);

	}

	public void atualizar(Funcionario funcionario) {

		funcionarioDAO.atualizar(funcionario);

	}

	public void excluir(Funcionario funcionario) {

		funcionarioDAO.excluir(funcionario);

	}

	public Funcionario buscarPorId(Long id) {

		Funcionario funcionario = null;

		return funcionario = (Funcionario) funcionarioDAO.buscarPorID(id);

	}

	public List<Funcionario> listar() {

		List<Funcionario> funcionarios = null;

		funcionarios = funcionarioDAO.listar();

		return funcionarios;

	}

	// método para realizar a busca por nome com CRITERIA
	public List<Funcionario> buscaPorNome(String nome) throws Exception {

		return funcionarioDAO.buscaPorNome(nome);

	}

	@RequestMapping(method = RequestMethod.GET, value = { "/{id}/remover" })
	public String remover(@PathVariable("id") Long id) {
		Funcionario funcionario = buscarPorId(id);
		if (funcionario != null) {
			try {
				excluir(funcionario);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "redirect:/funcionario/listar";
	}

}
