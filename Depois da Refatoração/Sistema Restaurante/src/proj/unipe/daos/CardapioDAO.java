package proj.unipe.daos;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import proj.unipe.entities.Cardapio;

@Repository
public class CardapioDAO extends AbstractDAO<Cardapio> {


	public CardapioDAO() {

		super(Cardapio.class);
	}


	public List<Cardapio> buscarPorNome(String filtro) {
		String sql = "select c from Cardapio c where upper(c.nome) like upper(:nome)";
		Query query = manager.createQuery(sql);
		query.setParameter("nome", "%"+filtro+"%");
		return query.getResultList();
	}
	
	public List<Cardapio> buscarCardapioPorCategoria(Cardapio filtro) {
		String sql = "select p from Cardapio p where p.categoria.id = :idCategoria";
		Query query = manager.createQuery(sql);
		query.setParameter("idCategoria", filtro.getCategoria().getId());
		return query.getResultList();
	}

	

}
