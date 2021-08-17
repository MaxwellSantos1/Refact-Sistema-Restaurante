package proj.unipe.entities;

//classe abstrata, onde todos os beans irão herdar dela para serem obrigados a implementar o método setId e getId
public abstract class AbstractEntity {
	//metodos abstratos que irão ser implementados nas classes beans que irão extender essa classe
	public abstract Long getId();
	public abstract void setId(Long id);
	
	@Override
	public boolean equals(Object obj) {
		AbstractEntity entidade = (AbstractEntity)  obj;
		if(this.getId() != null && this.getId() != 0 && entidade.getId() != null && entidade.getId() != 0 && this.getId().equals(entidade.getId())){
			return true;
		}
		return false;
	}	
	
	
}
