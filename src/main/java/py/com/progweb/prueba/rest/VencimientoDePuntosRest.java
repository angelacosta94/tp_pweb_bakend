package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.VencimientoDePuntosDAO;
import py.com.progweb.prueba.model.VencimientoDePuntos;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("vencimientos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class VencimientoDePuntosRest {
    @Inject
    private VencimientoDePuntosDAO vencimientoDePuntosDAO;
    @Context
    protected UriInfo uriInfo;


    @GET
    @Path("/")
    public Response listar() throws WebApplicationException {

        Long total = vencimientoDePuntosDAO.total();
        List<VencimientoDePuntos> listEntity = vencimientoDePuntosDAO.lista();
        Map<String, Object> mapaResultado = new HashMap<String, Object>();
        mapaResultado.put("total", total);
        mapaResultado.put("lista", listEntity);

        return Response.ok(mapaResultado).build();

    }

    @GET
    @Path("/dia/{fecha:[1-2][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]}")
    public Response consultarDiasVencimiento(@PathParam("fecha") String f) throws WebApplicationException, ParseException {
        Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(f);
        Integer dias = vencimientoDePuntosDAO.getDiasDuracion(fecha);

        Map<String, Object> mapaResultado = new HashMap<String, Object>();
        mapaResultado.put("dias", dias);

        return Response.ok(mapaResultado).build();

    }

    @GET
    @Path("/{pk}")
    public Response obtener(@PathParam("pk") Integer pk) {
        VencimientoDePuntos entityRespuesta = vencimientoDePuntosDAO.get(pk);
        return Response.ok(entityRespuesta).build();
    }

    @POST
    @Path("/")
    public Response crear(VencimientoDePuntos entity) throws WebApplicationException {

        vencimientoDePuntosDAO.persist(entity);

        UriBuilder resourcePathBuilder = UriBuilder.fromUri(uriInfo
                .getAbsolutePath());
        URI resourceUri = null;
        try {
            resourceUri = resourcePathBuilder
                    .path(URLEncoder.encode(entity.getIdVencimiento().toString(), "UTF-8")).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.created(resourceUri).build();
    }

    @PUT
    @Path("/")
    public Response modificar(VencimientoDePuntos entity) throws WebApplicationException {
        vencimientoDePuntosDAO.merge(entity);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{pk}")
    public Response borrar(@PathParam("pk") Integer pk) throws WebApplicationException {

        vencimientoDePuntosDAO.delete(pk);
        return Response.ok().build();

    }
}
