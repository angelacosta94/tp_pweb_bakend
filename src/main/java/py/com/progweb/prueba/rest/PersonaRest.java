package py.com.progweb.prueba.rest;

import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import py.com.progweb.prueba.ejb.PersonaDAO;
import py.com.progweb.prueba.model.Persona;

@Path("persona")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PersonaRest {	
	@Inject
	private PersonaDAO personaDAO;
	@Context
	protected UriInfo uriInfo;
	
	
	@GET
	@Path("/")
	public Response listar() throws WebApplicationException{
		
		List<Persona> listEntity = null;
		Long total = null;
		total = personaDAO.total();
		listEntity = personaDAO.lista();
		Map<String,Object> mapaResultado=new HashMap<String, Object>();
		mapaResultado.put("total", total);
		mapaResultado.put("lista", listEntity);
		
		return Response.ok(mapaResultado).build();
		
	}
	
	@GET
	@Path("/{pk}")
	public Response obtener(@PathParam("pk") Integer pk) {
		Persona entityRespuesta =null;
		entityRespuesta = personaDAO.get(pk);
		return Response.ok(entityRespuesta).build();
	}

	@POST
	@Path("/")
	public Response crear(Persona entity) throws WebApplicationException {
		personaDAO.persist(entity);
		
		UriBuilder resourcePathBuilder = UriBuilder.fromUri(uriInfo
				.getAbsolutePath());
		URI resourceUri=null;
		try {
			resourceUri = resourcePathBuilder
					.path(URLEncoder.encode(entity.getIdPersona().toString(), "UTF-8")).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.created(resourceUri).build();
	}

	@PUT
	@Path("/")
	public Response modificar(Persona entity) throws WebApplicationException {
		personaDAO.merge(entity);
		return Response.ok().build();
	}

	@DELETE
	@Path("/{pk}")
	public Response borrar(@PathParam("pk") Integer pk) throws WebApplicationException {
		
		personaDAO.delete(pk);
		return Response.ok().build();

	}
}
