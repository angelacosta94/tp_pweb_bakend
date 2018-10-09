package py.com.progweb.prueba.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import py.com.progweb.prueba.model.Agenda;
import py.com.progweb.prueba.model.Persona;

@Stateless
public class PersonaDAO {
	@PersistenceContext(unitName="pruebaPU")
    private EntityManager em;
	
	protected EntityManager getEm() {
		return em;
	}	
	
    public Persona get(Integer id) {
    		Persona p=em.find(Persona.class, id);
    		p.setListaAgendaTransient(new ArrayList<Agenda>());
    		for (Agenda a:p.getListaAgenda()) {
    			p.getListaAgendaTransient().add(a.clone());
    		}
        return p;
    }    
    
    public void persist(Persona entity){
        getEm().persist(entity);
    }    
    public Persona merge(Persona entity){
        return (Persona) getEm().merge(entity);
    }    
    public void delete(Integer id){
        Persona entity = this.get(id);
        this.getEm().remove(entity);
    }    
    public void delete(Persona entity){
    		this.delete(entity.getIdPersona());
    }  
    @SuppressWarnings("unchecked")
	public List<Persona> lista() {
    		Query q = getEm().createQuery(
    			"SELECT p FROM Persona p");
    		return (List<Persona>) q.getResultList();
    }
    public Long total() {
    		Query q = getEm().createQuery(
    			"Select Count(p) from Persona p");
    		return (Long) q.getSingleResult();
    }
}