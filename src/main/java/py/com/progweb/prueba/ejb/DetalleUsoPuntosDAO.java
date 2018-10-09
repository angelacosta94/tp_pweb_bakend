package py.com.progweb.prueba.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import py.com.progweb.prueba.model.DetalleUsoPuntos;

@Stateless
public class DetalleUsoPuntosDAO {
	@PersistenceContext(unitName="pruebaPU")
    private EntityManager em;
	
	protected EntityManager getEm() {
		return em;
	}	
	
    public DetalleUsoPuntos get(Integer id) {
        return em.find(DetalleUsoPuntos.class, id);
    }    
    
    public void persist(DetalleUsoPuntos entity){
        getEm().persist(entity);
    }    
    public DetalleUsoPuntos merge(DetalleUsoPuntos entity){
        return (DetalleUsoPuntos) getEm().merge(entity);
    }    
    public void delete(Integer id){
        DetalleUsoPuntos entity = this.get(id);
        this.getEm().remove(entity);
    }    
    public void delete(DetalleUsoPuntos entity){
    		this.delete(entity.getIdDetalleUsoPuntos());
    }  
    @SuppressWarnings("unchecked")
	public List<DetalleUsoPuntos> lista() {
    		Query q = getEm().createQuery(
    			"SELECT p FROM uso_puntos p");
    		return (List<DetalleUsoPuntos>) q.getResultList();
    }
    public Long total() {
    		Query q = getEm().createQuery(
    			"Select Count(p) from uso_puntos p");
    		return (Long) q.getSingleResult();
    }
}
