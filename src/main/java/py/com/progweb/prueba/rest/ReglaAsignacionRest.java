package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.ReglaAsignacionDAO;
import py.com.progweb.prueba.model.ReglaAsignacion;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("regla-asignacion")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ReglaAsignacionRest {
    @Inject
    private ReglaAsignacionDAO reglaAsignacionDAO;
    @Context
    protected UriInfo uriInfo;


    @GET
    @Path("/")
    public Response listar() throws WebApplicationException {

        Long total = reglaAsignacionDAO.total();
        List<ReglaAsignacion> listEntity = reglaAsignacionDAO.lista();
        Map<String, Object> mapaResultado = new HashMap<>();
        mapaResultado.put("total", total);
        mapaResultado.put("lista", listEntity);

        return Response.ok(mapaResultado).build();

    }

    @GET
    @Path("/{pk}")
    public Response obtener(@PathParam("pk") Integer pk) {
        ReglaAsignacion entityRespuesta = reglaAsignacionDAO.get(pk);
        return Response.ok(entityRespuesta).build();
    }

    @POST
    @Path("/")
    public Response crear(ReglaAsignacion entity) throws WebApplicationException {

        reglaAsignacionDAO.persist(entity);

        UriBuilder resourcePathBuilder = UriBuilder.fromUri(uriInfo
                .getAbsolutePath());
        URI resourceUri = null;
        try {
            resourceUri = resourcePathBuilder
                    .path(URLEncoder.encode(entity.getIdReglaAsignacion().toString(), "UTF-8")).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.created(resourceUri).build();
    }

    @PUT
    @Path("/")
    public Response modificar(ReglaAsignacion entity) throws WebApplicationException {
        reglaAsignacionDAO.merge(entity);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{pk}")
    public Response borrar(@PathParam("pk") Integer pk) throws WebApplicationException {

        reglaAsignacionDAO.delete(pk);
        return Response.ok().build();

    }
}
