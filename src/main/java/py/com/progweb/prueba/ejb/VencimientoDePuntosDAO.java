package py.com.progweb.prueba.ejb;

import py.com.progweb.prueba.model.BolsaDePuntos;
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
public class VencimientoDePuntosDAO {
    @Inject
    private BolsaDePuntosDAO bolsaDePuntosDAO;

    @PersistenceContext(unitName = "pruebaPU")
    private EntityManager em;

    protected EntityManager getEm() {
        return em;
    }

    public VencimientoDePuntos get(Integer id) {
        return em.find(VencimientoDePuntos.class, id);
    }

    public void persist(VencimientoDePuntos entity) {
        getEm().persist(entity);
        actualizarSaldoBolsaDePuntos(entity);
    }

    public VencimientoDePuntos merge(VencimientoDePuntos entity) {
        VencimientoDePuntos venc = getEm().merge(entity);
        actualizarSaldoBolsaDePuntos(entity);
        return venc;
    }

    public void delete(Integer id) {
        VencimientoDePuntos entity = this.get(id);
        this.getEm().remove(entity);
        actualizarSaldoBolsaDePuntos(entity);
    }

    public void delete(VencimientoDePuntos entity) {
        this.delete(entity.getIdVencimiento());
    }

    @SuppressWarnings("unchecked")
    public List<VencimientoDePuntos> lista() {
        Query q = getEm().createQuery(
                "SELECT v FROM VencimientoDePuntos v");
        return (List<VencimientoDePuntos>) q.getResultList();
    }

    public Long total() {
        Query q = getEm().createQuery(
                "Select Count(v) from VencimientoDePuntos v");
        return (Long) q.getSingleResult();
    }

    //Dias de duracion de una bolsa de puntos segun la fecha de asignacion
    public Integer getDiasDuracion(Date fecha) {
        Query q = getEm().createQuery(
                "select max(v.diasDuracion) from VencimientoDePuntos v where :fecha between v.inicioValidez and v.finValidez",
                Integer.class); //Si hay más de una regla para una misma fecha, elige la mayor duracion
        q.setParameter("fecha", fecha);

        try {
            return (Integer) q.getSingleResult();
        } catch (NoResultException e) {
            return null;  //Si no existen reglas para la fecha retorna null
        }
    }

    //Actualiza el saldo de las bolsas afectadas por una nueva regla de vencimiento
    private void actualizarSaldoBolsaDePuntos(VencimientoDePuntos vencimiento) {
        List<BolsaDePuntos> bolsas = bolsaDePuntosDAO.listarEnRango(vencimiento.getInicioValidez(), vencimiento.getFinValidez());
        Date fechaHoy = new Date();

        for (BolsaDePuntos bolsa : bolsas) {
            Date fechaAsignacion = bolsa.getFechaAsignacion();
            Integer diasDuracion = getDiasDuracion(fechaAsignacion);

            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaAsignacion);
            cal.add(Calendar.DATE, diasDuracion);

            //Para que deje usar los puntos hasta el final del día inclusive
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);

            Date fechaCaducidadNueva = cal.getTime();
            bolsa.setFechaCaducidad(fechaCaducidadNueva);

            if (fechaCaducidadNueva.before(fechaHoy)) { //Si la fecha de caducidad  ya paso la fecha actual
                bolsa.setSaldoPuntos(0); //Puntos vencidos
            } else {
                bolsa.setSaldoPuntos(bolsa.getPuntajeAsignado() - bolsa.getPuntajeUtilizado());
            }

            em.merge(bolsa);
        }
    }
}
