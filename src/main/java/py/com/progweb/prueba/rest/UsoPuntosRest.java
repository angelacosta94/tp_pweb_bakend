package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.ClienteDAO;
import py.com.progweb.prueba.ejb.ConceptoUsoPuntosDAO;
import py.com.progweb.prueba.ejb.UsoPuntosDAO;
import py.com.progweb.prueba.model.Cliente;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static py.com.progweb.prueba.rest.ExportUtils.crearExcel;
import static py.com.progweb.prueba.rest.ExportUtils.crearPDF;

@Path("uso-puntos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class UsoPuntosRest {
    @Context
    protected UriInfo uriInfo;
    @Inject
    private UsoPuntosDAO usoPuntosDAO;
    @Inject
    private ClienteDAO clienteDAO;
    @Inject
    private ConceptoUsoPuntosDAO conceptoUsoPuntosDAO;

    @GET
    @Path("/")
    public Response listar() throws WebApplicationException {
        List<UsoPuntos> listEntity = usoPuntosDAO.lista();
        Long total = usoPuntosDAO.total();

        Map<String, Object> mapaResultado = new HashMap<>();
        mapaResultado.put("total", total);
        mapaResultado.put("lista", listEntity);

        return Response.ok(mapaResultado).build();
    }

    @GET
    @Path("/cliente/{pkCliente}")
    public Response listarPorCliente(@PathParam("pkCliente") Integer pk, @QueryParam("export") String export) throws WebApplicationException {
        Cliente cliente = clienteDAO.get(pk);

        List<UsoPuntos> listEntity = usoPuntosDAO.listaPorCliente(cliente);
        if (listEntity == null)
            listEntity = new ArrayList<>();

        if (export != null && export.equalsIgnoreCase("PDF")){
            String titulo = "Uso de puntos del cliente " + cliente.getNombre();
            return crearPDF("usoPuntos", titulo, listEntity, "uso_de_puntos");
        } else if (export != null && export.equalsIgnoreCase("XLS")){
            String titulo = "Uso de puntos del cliente " + cliente.getNombre();
            return crearExcel("usoPuntos", titulo, listEntity, "uso_de_puntos");
        }

        long total = listEntity.size();

        Map<String, Object> mapaResultado = new HashMap<>();
        mapaResultado.put("total", total);
        mapaResultado.put("lista", listEntity);

        return Response.ok(mapaResultado).build();
    }


    @GET
    @Path("/fecha/{fecha:[1-2][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]}")
    public Response listarPorFecha(@PathParam("fecha") String f, @QueryParam("export") String export) throws WebApplicationException, ParseException {
        Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(f);

        List<UsoPuntos> listEntity = usoPuntosDAO.listaPorFechaUso(fecha);
        if (listEntity == null)
            listEntity = new ArrayList<>();

        if (export != null && export.equalsIgnoreCase("PDF")){
            String titulo = "Uso de puntos en la fecha " + f;
            return crearPDF("usoPuntos", titulo, listEntity, "uso_de_puntos");
        } else if (export != null && export.equalsIgnoreCase("XLS")){
            String titulo = "Uso de puntos en la fecha " + f;
            return crearExcel("usoPuntos", titulo, listEntity, "uso_de_puntos");
        }

        long total = listEntity.size();

        Map<String, Object> mapaResultado = new HashMap<>();
        mapaResultado.put("total", total);
        mapaResultado.put("lista", listEntity);

        return Response.ok(mapaResultado).build();
    }

    @GET
    @Path("/concepto/{pkConcepto}")
    public Response listarPorConcepto(@PathParam("pkConcepto") Integer pk, @QueryParam("export") String export) throws WebApplicationException {
        ConceptoUsoPuntos conceptoUsoPuntos = conceptoUsoPuntosDAO.get(pk);

        List<UsoPuntos> listEntity = usoPuntosDAO.listaPorConceptoUso(conceptoUsoPuntos);
        if (listEntity == null)
            listEntity = new ArrayList<>();


        if (export != null && export.equalsIgnoreCase("PDF")){
            String titulo = "Uso de puntos por el concepto " + conceptoUsoPuntos.getDescripcion();
            return crearPDF("usoPuntos", titulo, listEntity, "uso_de_puntos");
        } else if (export != null && export.equalsIgnoreCase("XLS")){
            String titulo = "Uso de puntos por el concepto " + conceptoUsoPuntos.getDescripcion();
            return crearExcel("usoPuntos", titulo, listEntity, "uso_de_puntos");
        }

        long total = listEntity.size();

        Map<String, Object> mapaResultado = new HashMap<>();
        mapaResultado.put("total", total);
        mapaResultado.put("lista", listEntity);

        return Response.ok(mapaResultado).build();
    }

    @GET
    @Path("/{pk}")
    public Response obtener(@PathParam("pk") Integer pk) {
        UsoPuntos entityRespuesta = usoPuntosDAO.get(pk);
        return Response.ok(entityRespuesta).build();
    }

    @POST
    @Path("/")
    public Response crear(UsoPuntos entity) throws WebApplicationException {
        usoPuntosDAO.persist(entity);

        UriBuilder resourcePathBuilder = UriBuilder.fromUri(uriInfo
                .getAbsolutePath());
        URI resourceUri = null;
        try {
            resourceUri = resourcePathBuilder
                    .path(URLEncoder.encode(entity.getIdUsoPuntos().toString(), "UTF-8")).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.created(resourceUri).build();
    }

    @PUT
    @Path("/")
    public Response modificar(UsoPuntos entity) throws WebApplicationException {
        usoPuntosDAO.merge(entity);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{pk}")
    public Response borrar(@PathParam("pk") Integer pk) throws WebApplicationException {
        usoPuntosDAO.delete(pk);
        return Response.ok().build();
    }
}
