package py.com.progweb.prueba.rest;


import py.com.progweb.prueba.ejb.BolsaDePuntosDAO;
import py.com.progweb.prueba.ejb.ClienteDAO;
import py.com.progweb.prueba.model.BolsaDePuntos;
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
import java.util.List;

import static py.com.progweb.prueba.rest.ExportUtils.crearExcel;
import static py.com.progweb.prueba.rest.ExportUtils.crearPDF;


@Path("bolsa")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class BolsaDePuntosRest {
	@Inject
	private ClienteDAO clienteDAO;
	@Inject
	private BolsaDePuntosDAO bolsaDAO;
	@Context
	protected UriInfo uriInfo;
	
	@POST
	@Path("/")
	public Response crear(BolsaDePuntos entity) throws WebApplicationException {
		
		bolsaDAO.persist(entity);
		
		UriBuilder resourcePathBuilder = UriBuilder.fromUri(uriInfo
				.getAbsolutePath());
		URI resourceUri=null;
		try {
			resourceUri = resourcePathBuilder
					.path(URLEncoder.encode(entity.getIdBolsaPuntos().toString(), "UTF-8")).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.created(resourceUri).build();
	}

	
	@GET
	@Path("/cliente")
	public Response listarPorCliente(@QueryParam("idCliente") Integer idCliente, @QueryParam("export") String export) throws WebApplicationException{
		List<BolsaDePuntos> listEntity= new ArrayList<BolsaDePuntos>();
		Cliente c= clienteDAO.get(idCliente);

		listEntity=c.getListaBolsa();
		try {
			 listEntity=c.getListaBolsa();
		}catch(Exception e) {
			
		}

		if (export != null && export.equalsIgnoreCase("PDF")){
			String titulo = "Bolsa de puntos del cliente " + c.getNombre();
			return crearPDF("bolsaDePuntos", titulo, listEntity, "bolsa_de_puntos");
		} else if (export != null && export.equalsIgnoreCase("XLS")){
			String titulo = "Bolsa de puntos del cliente " + c.getNombre();
			return crearExcel("bolsaDePuntos", titulo, listEntity, "bolsa_de_puntos");
		}

		return Response.ok(listEntity).build();
		
	}
	
	@GET
	@Path("/rango")
	public Response listarPorRango(@QueryParam("valor1") Integer valor1, @QueryParam("valor2") Integer valor2, @QueryParam("export") String export) throws WebApplicationException{
		
		List<BolsaDePuntos> listEntity= new ArrayList<BolsaDePuntos>();
		List<BolsaDePuntos> listAux= new ArrayList<BolsaDePuntos>();
		listAux = bolsaDAO.lista();
		
		try {
			for (BolsaDePuntos b: listAux) {
				if(b.getSaldoPuntos()>= valor1 && b.getSaldoPuntos()<=valor2) {
					listEntity.add(b);
				}
			}
			 
		}catch(Exception e) {
			
		}

		if (export != null && export.equalsIgnoreCase("PDF")){
			String titulo = "Bolsa de puntos en el rango " + valor1 + " - " + valor2;
			return crearPDF("bolsaDePuntos", titulo, listEntity, "bolsa_de_puntos");
		} else if (export != null && export.equalsIgnoreCase("XLS")){
			String titulo = "Bolsa de puntos en el rango " + valor1 + " - " + valor2;
			return crearExcel("bolsaDePuntos", titulo, listEntity, "bolsa_de_puntos");
		}

		return Response.ok(listEntity).build();
		
	}

}
