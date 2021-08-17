package proj.unipe.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Query;
import javax.persistence.SequenceGenerator;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import proj.unipe.controllers.LoginController;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(sequenceName = "usuario_seq", name = "usuario_id", allocationSize = 1)
public class Usuario extends AbstractEntity{
 
	@Id 
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="usuario_id")
	private Long id;
	
	@Column( length = 20)
	private String login;
	
	@Column(length = 20)
	private String senha;
	
	@NotNull(message="Campo obrigatório")
	@Column(length = 20)
	private String nome;
	
	@Column( length = 20)
	private String email;
	
	@Column(length = 20)
	private String telefone;

	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public String entrar(LoginController loginController, HttpSession sessao) {
		
		Usuario usuarioBD = loginController.usuarioService.validaLogin(this);
	
		if (usuarioBD == null) {
			
			return "redirect:/telaLogin";
		} else{
			
			sessao.setAttribute("usuario", usuarioBD);
			sessao.setMaxInactiveInterval(1000);
			
			return "redirect:/cliente/listar";
		}
	
	}

	public Usuario validaLogin(EntityManager manager) {
		String s = "select u from Usuario u where u.login = :login and u.senha = :senha";
		Query query = manager.createQuery(s);
		query.setParameter("login", getLogin());
		query.setParameter("senha", getSenha());
		List<Usuario> usuarios = query.getResultList();
		if (!usuarios.isEmpty()) {
			return usuarios.get(0);
		}
		return null;
	}

}
