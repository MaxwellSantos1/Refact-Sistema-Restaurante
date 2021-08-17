package proj.unipe.daos;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import proj.unipe.entities.Usuario;

@Repository
public class UsuarioDAO extends AbstractDAO {

	
	@SuppressWarnings("unchecked")
	public UsuarioDAO() {
		super(Usuario.class);
		
	}
	
	public Usuario validaLogin(Usuario usuario) {
		return usuario.validaLogin(manager);
	}
	
}
