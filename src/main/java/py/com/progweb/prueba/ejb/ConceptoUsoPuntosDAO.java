package py.com.progweb.prueba.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import py.com.progweb.prueba.model.ConceptoUsoPuntos;


@Stateless
public class ConceptoUsoPuntosDAO {
	@PersistenceContext(unitName="pruebaPU")
    private EntityManager em;
	
	protected EntityManager getEm() {
		return em;
	}	
	
    public ConceptoUsoPuntos get(Integer id) {
        return em.find(ConceptoUsoPuntos.class, id);
    }    
    
    public void persist(ConceptoUsoPuntos entity){
        getEm().persist(entity);
    }    
    public ConceptoUsoPuntos merge(ConceptoUsoPuntos entity){
        return (ConceptoUsoPuntos) getEm().merge(entity);
    }    
    public void delete(Integer id){
    	ConceptoUsoPuntos entity = this.get(id);
        this.getEm().remove(entity);
    }    
    public void delete(ConceptoUsoPuntos entity){
    		this.delete(entity.getIdConceptoUsoPuntos());
    }  
    @SuppressWarnings("unchecked")
	public List<ConceptoUsoPuntos> lista() {
    		Query q = getEm().createQuery(
    			"SELECT p FROM ConceptoUsoPuntos p");
    		return (List<ConceptoUsoPuntos>) q.getResultList();
    }
    public Long total() {
    		Query q = getEm().createQuery(
    			"Select Count(p) from ConceptoUsoPuntos p");
    		return (Long) q.getSingleResult();
    }
    
}
