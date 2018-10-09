package py.com.progweb.prueba.rest;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import py.com.progweb.prueba.ejb.BolsaDePuntosDAO;
import py.com.progweb.prueba.ejb.ClienteDAO;
import py.com.progweb.prueba.ejb.ReglaAsignacionDAO;
import py.com.progweb.prueba.ejb.VencimientoDePuntosDAO;
import py.com.progweb.prueba.model.BolsaDePuntos;
import py.com.progweb.prueba.model.Cliente;


@Path("carga")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class CargaREST {
	@Context
	  SecurityContext sc;
    @Inject
    private ClienteDAO clienteDAO;

    @Inject
    private VencimientoDePuntosDAO vencimientoDePuntosDAO;

    @Inject
    private BolsaDePuntosDAO bolsaDePuntosDAO;
    @Inject
    private ReglaAsignacionDAO reglaDAO;

    @POST
    @Path("/")
    public Response calcularPuntos(@QueryParam("cliente") Integer idCliente, @QueryParam("monto") Integer monto) {
        Date fechaHoy = new Date();
        Integer diasDuracionPuntos = vencimientoDePuntosDAO.getDiasDuracion(fechaHoy);

        if (diasDuracionPuntos == null) //Si no existe ninguna regla para ese día
            diasDuracionPuntos = 30; //Por default

        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaHoy);
        cal.add(Calendar.DATE, diasDuracionPuntos);
        Date fechaCaducidad = cal.getTime();

        Cliente cliente = clienteDAO.get(idCliente);

        BolsaDePuntos bolsa = new BolsaDePuntos();
        bolsa.setCliente(cliente);
        bolsa.setFechaAsignacion(fechaHoy);
        bolsa.setFechaCaducidad(fechaCaducidad);
        bolsa.setMontoOperacion(monto);
        bolsa.setPuntajeAsignado(reglaDAO.getEquivalenciaPuntos(monto));
        bolsa.setPuntajeUtilizado(0);
        bolsa.setSaldoPuntos(bolsa.getPuntajeAsignado());
        bolsa.setUsuario(sc.getUserPrincipal().getName());
        bolsa.setSelloTiempo(new Timestamp(System.currentTimeMillis()));
        bolsaDePuntosDAO.persist(bolsa);

        return Response.ok().build();
    }

}

