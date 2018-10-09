package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.BolsaDePuntos;
import py.com.progweb.prueba.model.ReglaAsignacion;
import py.com.progweb.prueba.model.VencimientoDePuntos;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Stateless
public class ReglaAsignacionDAO {
    @Inject
    private BolsaDePuntosDAO bolsaDePuntosDAO;

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    protected EntityManager getEm() {
        return em;
    }

    public ReglaAsignacion get(Integer id) {
        return em.find(ReglaAsignacion.class, id);
    }

    public void persist(ReglaAsignacion entity) {
        getEm().persist(entity);
    }

    public ReglaAsignacion merge(ReglaAsignacion entity) {
    	ReglaAsignacion reg = getEm().merge(entity);
        return reg;
    }

    public void delete(Integer id) {
    	ReglaAsignacion entity = this.get(id);
        this.getEm().remove(entity);
    }

    public void delete(VencimientoDePuntos entity) {
        this.delete(entity.getIdVencimiento());
    }

    @SuppressWarnings("unchecked")
    public List<ReglaAsignacion> lista() {
        Query q = getEm().createQuery(
                "SELECT r FROM ReglaAsignacion r");
        return (List<ReglaAsignacion>) q.getResultList();
    }

    public Long total() {
        Query q = getEm().createQuery(
                "Select Count(r) from ReglaAsignacion r");
        return (Long) q.getSingleResult();
    }
    
    public Integer getEquivalenciaPuntos(Integer monto) {
        Query q = getEm().createQuery(
                "select r.valor from ReglaAsignacion r where :monto between r.desde and r.hasta",
                Integer.class); 
        q.setParameter("monto", monto);

        try {
            return monto/(Integer) q.getSingleResult();
        } catch (NoResultException e) {
            return null;  
        }
    }
}
