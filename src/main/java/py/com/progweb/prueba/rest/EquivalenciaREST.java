package py.com.progweb.prueba.rest;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


import py.com.progweb.prueba.ejb.ReglaAsignacionDAO;
import py.com.progweb.prueba.model.ReglaAsignacion;

@Path("puntos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EquivalenciaREST {
	@Inject
	private ReglaAsignacionDAO reglaDAO;
	@Context
	protected UriInfo uriInfo;

	@GET
	@Path("/equivalencia")
	public Response calcularPuntos(@QueryParam("monto") Integer monto) {
		Integer puntos=reglaDAO.getEquivalenciaPuntos(monto);
		
		return Response.ok(puntos).build();
	}

}

