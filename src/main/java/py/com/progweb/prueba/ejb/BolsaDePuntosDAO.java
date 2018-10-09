package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.BolsaDePuntos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Stateless
public class BolsaDePuntosDAO {
    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    protected EntityManager getEm() {
        return em;
    }

    public BolsaDePuntos get(Integer id) {
        return em.find(BolsaDePuntos.class, id);
    }

    public void persist(BolsaDePuntos entity) {
        getEm().persist(entity);
    }

    public BolsaDePuntos merge(BolsaDePuntos entity) {
        return getEm().merge(entity);
    }

    public void delete(Integer id) {
        BolsaDePuntos entity = this.get(id);
        this.getEm().remove(entity);
    }

    public void delete(BolsaDePuntos entity) {
        this.delete(entity.getIdBolsaPuntos());
    }

    @SuppressWarnings("unchecked")
    public List<BolsaDePuntos> lista() {
        Query q = getEm().createQuery(
                "SELECT b FROM BolsaDePuntos b ORDER by b.fechaCaducidad asc");
        return (List<BolsaDePuntos>) q.getResultList();
    }

    public void actualizarSaldoCaducados() {
        Query q = getEm().createQuery(
                "update BolsaDePuntos b set b.saldoPuntos = 0 where b.fechaCaducidad < :fecha");

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        //Para que deje usar los puntos hasta el final del día inclusive
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);

        q.setParameter("fecha", cal.getTime());
        q.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public List<BolsaDePuntos> listarEnRango(Date inicio, Date fin) {
        Query q = getEm().createQuery(
                "SELECT b FROM BolsaDePuntos b where b.fechaAsignacion between :fechaInicio and :fechaFin", BolsaDePuntos.class);
        q.setParameter("fechaInicio", inicio);
        q.setParameter("fechaFin", fin);
        return q.getResultList();
    }
}
