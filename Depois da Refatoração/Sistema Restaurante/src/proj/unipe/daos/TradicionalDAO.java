package proj.unipe.daos;

import org.springframework.stereotype.Repository;

import proj.unipe.entities.Tradicional;

@Repository
public class TradicionalDAO extends AbstractDAO<Tradicional> {

	
	public TradicionalDAO() {
		super(Tradicional.class);

	}
	
	

}
