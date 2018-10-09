package py.com.progweb.prueba.rest;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.mail.*;
import javax.mail.internet.*;

import py.com.progweb.prueba.ejb.BolsaDePuntosDAO;
import py.com.progweb.prueba.ejb.ClienteDAO;
import py.com.progweb.prueba.ejb.ConceptoUsoPuntosDAO;
import py.com.progweb.prueba.ejb.DetalleUsoPuntosDAO;
import py.com.progweb.prueba.ejb.UsoPuntosDAO;
import py.com.progweb.prueba.model.UsoPuntos;
import py.com.progweb.prueba.model.BolsaDePuntos;
import py.com.progweb.prueba.model.Cliente;
import py.com.progweb.prueba.model.ConceptoUsoPuntos;
import py.com.progweb.prueba.model.DetalleUsoPuntos;

@Path("puntos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class UtilizarPuntosREST {
	@Context
	  SecurityContext sc;
	@Inject
	private UsoPuntosDAO usoPuntosDAO;
	@Inject
	private ClienteDAO clienteDAO;
	@Inject
	private ConceptoUsoPuntosDAO conceptoDAO;
	@Inject
	private BolsaDePuntosDAO bolsaDAO;
	@Inject
	private DetalleUsoPuntosDAO detalleDAO;
	@Context
	protected UriInfo uriInfo;

	@GET
	@Path("/utilizarPuntos")
	public Response usarPuntos(@QueryParam("cliente") Integer idCliente, @QueryParam("concepto") Integer idConcepto) {

		ConceptoUsoPuntos entityConcepto = conceptoDAO.get(idConcepto);
		Cliente entityCliente = clienteDAO.get(idCliente);
		Integer costo = entityConcepto.getPuntosRequeridos();
		List<BolsaDePuntos> bolsas = entityCliente.getListaBolsa();
		
		Integer puntosDisp=0;
		for (BolsaDePuntos b : bolsas) {
			puntosDisp+=b.getSaldoPuntos();
		}
		
		if (puntosDisp>=costo) {
			UsoPuntos entityUsoPuntos = new UsoPuntos();
			// completar los datos del UsoPuntos y guardar
			Date date = new Date();
			entityUsoPuntos.setFecha(date);
			entityUsoPuntos.setCliente(entityCliente);
			entityUsoPuntos.setConceptoUsoPuntos(entityConcepto);
			entityUsoPuntos.setPuntosUtilizados(entityConcepto.getPuntosRequeridos());
			entityUsoPuntos.setUsuario(sc.getUserPrincipal().getName());
			entityUsoPuntos.setSelloTiempo(new Timestamp(System.currentTimeMillis()));
			usoPuntosDAO.persist(entityUsoPuntos);
			// sacar los puntos de las bolsas y generar el detalle por cada bolsa
			for (BolsaDePuntos b : bolsas) {
				if (costo > 0) {
					int costoAnterior = costo;
					if (b.getSaldoPuntos() != 0) {
						if (b.getSaldoPuntos() >= costo) {
							b.setSaldoPuntos(b.getSaldoPuntos() - costo);
							b.setPuntajeUtilizado(b.getPuntajeUtilizado() + costo);
							bolsaDAO.merge(b);
							costo = 0;
						} else {
							costo -= b.getSaldoPuntos();
							b.setSaldoPuntos(0);
							b.setPuntajeUtilizado(b.getPuntajeAsignado());
							bolsaDAO.merge(b);
						}
						DetalleUsoPuntos d = new DetalleUsoPuntos();
						d.setBolsa(b);
						d.setUsoPuntos(entityUsoPuntos);
						d.setPuntajeUsado(costoAnterior - costo);
						detalleDAO.persist(d);
					}
				}
			}
			// enviar correo
			final String username = "tp1pwb@gmail.com";
			final String password = "tp1pwbtp1pwb";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("tp1pwb@gmail.com"));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(entityCliente.getEmail()));
				message.setSubject("Uso de puntos - Sistema de Fidelización");
				message.setText("Has usado " + entityConcepto.getPuntosRequeridos() + " puntos en "
						+ entityConcepto.getDescripcion());
				Transport.send(message);
				System.out.println("Correo enviado....");
			} catch (MessagingException mex) {
				mex.printStackTrace();
			}

		}else {
			System.out.println("Error: no hay puntos suficientes");
			return Response.ok().build();
		}
		
		return Response.ok().build();
	}

}
