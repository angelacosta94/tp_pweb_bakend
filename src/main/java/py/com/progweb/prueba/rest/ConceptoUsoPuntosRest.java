package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.ConceptoUsoPuntosDAO;
import py.com.progweb.prueba.model.ConceptoUsoPuntos;
import py.com.progweb.prueba.model.UsoPuntos;

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

@Path("conceptos-uso")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ConceptoUsoPuntosRest {
    @Context
    protected UriInfo uriInfo;
    @Inject
    private ConceptoUsoPuntosDAO conceptoUsoPuntosDAO;

    @GET
    @Path("/")
    public Response listar() throws WebApplicationException {
        List<ConceptoUsoPuntos> listEntity = conceptoUsoPuntosDAO.lista();
        Long total = conceptoUsoPuntosDAO.total();
        Map<String, Object> mapaResultado = new HashMap<>();

        mapaResultado.put("total", total);
        mapaResultado.put("lista", listEntity);

        return Response.ok(mapaResultado).build();
    }

    @GET
    @Path("/{pk}")
    public Response obtener(@PathParam("pk") Integer pk) {
        ConceptoUsoPuntos entityRespuesta = conceptoUsoPuntosDAO.get(pk);
        if(entityRespuesta != null)
            return Response.ok(entityRespuesta).build();
        else
            return Response.status(404).build(); //No existe concepto con ese pk
    }

    @GET
    @Path("/{pkConcepto}/lista-usos")
    public Response obtenerListaUsoPuntos(@PathParam("pkConcepto") Integer pkConcepto) throws WebApplicationException  {
        ConceptoUsoPuntos concepto = conceptoUsoPuntosDAO.get(pkConcepto);
        if(concepto == null){
            return Response.status(404).build(); //No existe concepto con ese pk
        }

        List<UsoPuntos> listaUsoPuntos = concepto.getUsoPuntosPorConcepto();

        Map<String, Object> mapaResultado = new HashMap<>();

        mapaResultado.put("total", listaUsoPuntos.size());
        mapaResultado.put("lista", listaUsoPuntos);

        return Response.ok(mapaResultado).build();
    }

    @POST
    @Path("/")
    public Response crear(ConceptoUsoPuntos entity) throws WebApplicationException {
        conceptoUsoPuntosDAO.persist(entity);

        UriBuilder resourcePathBuilder = UriBuilder.fromUri(uriInfo
                .getAbsolutePath());
        URI resourceUri = null;
        try {
            resourceUri = resourcePathBuilder
                    .path(URLEncoder.encode(entity.getIdConceptoUsoPuntos().toString(), "UTF-8")).build();
            return Response.created(resourceUri).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).build(); //Internal Server Error
        }
    }

    @PUT
    @Path("/")
    public Response modificar(ConceptoUsoPuntos entity) throws WebApplicationException {
        conceptoUsoPuntosDAO.merge(entity);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{pk}")
    public Response borrar(@PathParam("pk") Integer pk) throws WebApplicationException {
        conceptoUsoPuntosDAO.delete(pk);
        return Response.ok().build();
    }
}
