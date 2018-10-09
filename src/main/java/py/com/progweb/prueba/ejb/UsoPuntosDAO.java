package py.com.progweb.prueba.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import py.com.progweb.prueba.model.Cliente;
import py.com.progweb.prueba.model.ConceptoUsoPuntos;
import py.com.progweb.prueba.model.UsoPuntos;

@Stateless
public class UsoPuntosDAO {
	@PersistenceContext(unitName="pruebaPU")
    private EntityManager em;
	
	protected EntityManager getEm() {
		return em;
	}	
	
    public UsoPuntos get(Integer id) {
        return em.find(UsoPuntos.class, id);
    }    
    
    public void persist(UsoPuntos entity){
        getEm().persist(entity);
    }    
    public UsoPuntos merge(UsoPuntos entity){
        return (UsoPuntos) getEm().merge(entity);
    }    
    public void delete(Integer id){
        UsoPuntos entity = this.get(id);
        this.getEm().remove(entity);
    }    
    public void delete(UsoPuntos entity){
    		this.delete(entity.getIdUsoPuntos());
    }  
    @SuppressWarnings("unchecked")
	public List<UsoPuntos> lista() {
    		Query q = getEm().createQuery(
    			"SELECT p FROM UsoPuntos p");
    		return (List<UsoPuntos>) q.getResultList();
    }
    public Long total() {
    		Query q = getEm().createQuery(
    			"Select Count(p) from UsoPuntos p");
    		return (Long) q.getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public List<UsoPuntos> listaPorConceptoUso(ConceptoUsoPuntos concepto) {
        Query q = getEm().createQuery(
                "SELECT u FROM UsoPuntos u where u.conceptoUsoPuntos = :concepto", UsoPuntos.class);
        q.setParameter("concepto", concepto);
        return q.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<UsoPuntos> listaPorCliente(Cliente cliente) {
        Query q = getEm().createQuery(
                "SELECT u FROM UsoPuntos u where u.cliente = :cliente", UsoPuntos.class);
        q.setParameter("cliente", cliente);
        return  q.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<UsoPuntos> listaPorFechaUso(Date fecha) {
        Query q = getEm().createQuery(
                "SELECT u FROM UsoPuntos u where u.fecha = :fecha", UsoPuntos.class);
        q.setParameter("fecha", fecha);
        return  q.getResultList();
    }
}
