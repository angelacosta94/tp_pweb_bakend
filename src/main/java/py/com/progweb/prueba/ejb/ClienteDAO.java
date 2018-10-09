package py.com.progweb.prueba.ejb;

import java.util.Date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import py.com.progweb.prueba.model.Cliente;

@Stateless
public class ClienteDAO {
	@PersistenceContext(unitName="pruebaPU")
    private EntityManager em;
	
	protected EntityManager getEm() {
		return em;
	}	
	
    public Cliente get(Integer id) {
        return em.find(Cliente.class, id);
    }    
    
    public void persist(Cliente entity){
        getEm().persist(entity);
    }    
    public Cliente merge(Cliente entity){
        return (Cliente) getEm().merge(entity);
    }    
    public void delete(Integer id){
        Cliente entity = this.get(id);
        this.getEm().remove(entity);
    }    
    public void delete(Cliente entity){
    		this.delete(entity.getIdCliente());
    }  
    @SuppressWarnings("unchecked")
	public List<Cliente> lista() {
    		Query q = getEm().createQuery(
    			"SELECT c FROM Cliente c");
    		return (List<Cliente>) q.getResultList();
    }
    public Long total() {
    		Query q = getEm().createQuery(
    			"Select Count(c) from Cliente c");
    		return (Long) q.getSingleResult();
    }
    
    //Retorna los clientes por su fecha de nacimiento
    @SuppressWarnings("unchecked")
    public List<Cliente> getClienteFechaNacimiento(String fechac) {
        Query q = getEm().createQuery(
                "select c from Cliente c where :fecha = to_char(c.fechaNacimiento, 'MM-dd')",
                Cliente.class); 
        q.setParameter("fecha", fechac);

        try {
            return (List<Cliente>) q.getResultList();
        } catch (NoResultException e) {
            return null; 
        }
    }

    
    //Retorna los clientes por su nacionalidad
    @SuppressWarnings("unchecked")
    public List<Cliente> getClienteNacionalidad(String nacionalidad) {
        Query q = getEm().createQuery(
                "select c from Cliente c where :nacionalidad = c.nacionalidad",
                Cliente.class); 
        q.setParameter("nacionalidad", nacionalidad);

        try {
            return (List<Cliente>) q.getResultList();
        } catch (NoResultException e) {
            return null; 
        }
    }
    //Retorna los clientes por su documento
    @SuppressWarnings("unchecked")
    public Cliente getClienteDocumento(String documento) {
        Query q = getEm().createQuery(
                "select c from Cliente c where :documento = c.nroDocumento",
                Cliente.class); 
        q.setParameter("documento", new Integer(documento));
        
        try {
            return (Cliente) q.getSingleResult();
        } catch (NoResultException e) {
            return null; 
        }
    }

    
    @SuppressWarnings("unchecked")
	public List<Cliente> getVencimientos(Integer dias) {
    	Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, dias);
        Date fechaCaducidad = cal.getTime();
        Query q = getEm().createQuery(
                "select c from Cliente c, BolsaDePuntos b where c.idCliente = b.cliente.idCliente and :fechaCaducidad > b.fechaCaducidad",
                Cliente.class);
        q.setParameter("fechaCaducidad", fechaCaducidad);

        try {
            return (List<Cliente>) q.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

}
