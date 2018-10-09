package py.com.progweb.prueba.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import py.com.progweb.prueba.model.Agenda;

@Stateless
public class AgendaDAO {
	@PersistenceContext(unitName="pruebaPU")
    private EntityManager em;
	protected EntityManager getEm() {
		return em;
	}
	public Agenda get(Integer id) {
        return em.find(Agenda.class, id);
    }    
    
    public void persist(Agenda entity){
        getEm().persist(entity);
    }    
    public Agenda merge(Agenda entity){
        return (Agenda) getEm().merge(entity);
    }    
    public void delete(Integer id){
        Agenda entity = this.get(id);
        this.getEm().remove(entity);
    }    
    public void delete(Agenda entity){
    		this.delete(entity.getIdAgenda());
    }  
    @SuppressWarnings("unchecked")
	public List<Agenda> lista() {
    		Query q = getEm().createQuery(
    			"SELECT p FROM Agenda p");
    		return (List<Agenda>) q.getResultList();
    }
    public Long total() {
    		Query q = getEm().createQuery(
    			"Select Count(p) from Agenda p");
    		return (Long) q.getSingleResult();
    }
}
