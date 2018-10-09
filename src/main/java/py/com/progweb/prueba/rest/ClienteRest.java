package py.com.progweb.prueba.rest;

import py.com.progweb.prueba.ejb.ClienteDAO;
import py.com.progweb.prueba.model.Cliente;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static py.com.progweb.prueba.rest.ExportUtils.crearExcel;
import static py.com.progweb.prueba.rest.ExportUtils.crearPDF;

@Path("clientes")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ClienteRest {	
	@Inject
	private ClienteDAO clienteDAO;
	@Context
	protected UriInfo uriInfo;
	
	
	@GET
	@Path("/")
	public Response listar() throws WebApplicationException{
		
		List<Cliente> listEntity = null;
		Long total = null;
		listEntity = clienteDAO.lista();
		Map<String,Object> mapaResultado=new HashMap<String, Object>();
		mapaResultado.put("total", total);
		mapaResultado.put("lista", listEntity);
		
		return Response.ok(mapaResultado).build();
		
	}
	
	@GET
	@Path("/{pk}")
	public Response obtener(@PathParam("pk") Integer pk) {
		Cliente entityRespuesta =null;
		entityRespuesta = clienteDAO.get(pk);
		return Response.ok(entityRespuesta).build();
	}

	@GET
	@Path("/vencepuntos")
	public Response vencimientos(@QueryParam("dias") Integer dias, @QueryParam("export") String export) {
		List<Cliente> entityRespuesta =null;
		entityRespuesta = clienteDAO.getVencimientos(dias);

        if (export != null && export.equalsIgnoreCase("PDF")){
            String titulo = "Clientes con puntos que vencen en " + dias + " dias";
            return crearPDF("clientes", titulo, entityRespuesta, "clientes");
        } else if (export != null && export.equalsIgnoreCase("XLS")){
            String titulo = "Clientes con puntos que vencen en " + dias + " dias";
            return crearExcel("clientes", titulo, entityRespuesta, "clientes");
        }

		return Response.ok(entityRespuesta).build();
	}
	
	@POST
	@Path("/")
	public Response crear(Cliente entity) throws WebApplicationException {
		
		clienteDAO.persist(entity);
		
		UriBuilder resourcePathBuilder = UriBuilder.fromUri(uriInfo
				.getAbsolutePath());
		URI resourceUri=null;
		try {
			resourceUri = resourcePathBuilder
					.path(URLEncoder.encode(entity.getIdCliente().toString(), "UTF-8")).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.created(resourceUri).build();
	}

	@PUT
	@Path("/")
	public Response modificar(Cliente entity) throws WebApplicationException {
		clienteDAO.merge(entity);
		return Response.ok().build();
	}

	@DELETE
	@Path("/{pk}")
	public Response borrar(@PathParam("pk") Integer pk) throws WebApplicationException {
		
		clienteDAO.delete(pk);
		return Response.ok().build();

	}
	
    @GET
    @Path("/cumple")
    public Response consultarClienteCumpleanos(@QueryParam("fecha") String f, @QueryParam("export") String export)throws WebApplicationException {

	        List<Cliente> listEntity = clienteDAO.getClienteFechaNacimiento(f);


            if (export != null && export.equalsIgnoreCase("PDF")){
                String titulo = "Clientes que cumplen años el " + f;
                return crearPDF("clientes", titulo, listEntity, "clientes");
            } else if (export != null && export.equalsIgnoreCase("XLS")){
                String titulo = "Clientes que cumplen años el " + f;
                return crearExcel("clientes", titulo, listEntity, "clientes");
            }

	        Map<String, Object> mapaResultado = new HashMap<String, Object>();
	        
	        mapaResultado.put("lista", listEntity);
			return Response.ok(mapaResultado).build();

    }	
	
    @GET
    @Path("/nacionalidad")
    public Response consultarClienteNacionalidad(@QueryParam("nacionalidad") String nac,  @QueryParam("export") String export)throws WebApplicationException {

       
        List<Cliente> listEntity = clienteDAO.getClienteNacionalidad(nac);

        if (export != null && export.equalsIgnoreCase("PDF")){
            String titulo = "Clientes con nacionalidad " + nac;
            return crearPDF("clientes", titulo, listEntity, "clientes");
        } else if (export != null && export.equalsIgnoreCase("XLS")){
            String titulo = "Clientes con nacionalidad " + nac;
            return crearExcel("clientes", titulo, listEntity, "clientes");
        }

        Map<String, Object> mapaResultado = new HashMap<String, Object>();
        
        mapaResultado.put("lista", listEntity);

        return Response.ok(mapaResultado).build();

    }	
    
    @GET
    @Path("/documento")
    @SuppressWarnings("unchecked")
    public Response consultarClienteDocumento(@QueryParam("documento") String doc, @QueryParam("export") String export)throws WebApplicationException {

        Cliente entity = null;
        entity = clienteDAO.getClienteDocumento(doc);
        if (export != null && export.equalsIgnoreCase("PDF")){
            ArrayList lista =  new ArrayList<Cliente>();
            lista.add(entity);
            String titulo = "Clientes con documento " + doc;
            return crearPDF("clientes", titulo, lista, "clientes");
        } else if (export != null && export.equalsIgnoreCase("XLS")){
            ArrayList lista =  new ArrayList<Cliente>();
            lista.add(entity);
            String titulo = "Clientes con documento " + doc;
            return crearExcel("clientes", titulo, lista, "clientes");
        }
        return Response.ok(entity).build();
       
       

    }
}
