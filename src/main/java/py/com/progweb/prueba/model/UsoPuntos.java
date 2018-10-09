package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="uso_puntos")
public class UsoPuntos implements Serializable {

	private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_uso_puntos")
    @GeneratedValue(generator="usoPuntosSec")
    @SequenceGenerator(name="usoPuntosSec",sequenceName="uso_puntos_sec",allocationSize=0)
    private Integer idUsoPuntos;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_cliente")
    private Cliente cliente;
    
    @Basic(optional = false)
    @Column(name = "puntos_utilizados")
    private Integer puntosUtilizados;
    
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT-4")
    private Date fecha;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_concepto_uso_puntos")
    private ConceptoUsoPuntos conceptoUsoPuntos;
    
    @OneToMany(mappedBy = "usoPuntos")
	@JsonIgnore
	private List<DetalleUsoPuntos> detalles;
    @Column(name = "usuario", length = 50)
	private String usuario;
    @Column(name = "sello_tiempo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date selloTiempo;
    
    public UsoPuntos() {
    	
    }

	public Integer getIdUsoPuntos() {
		return idUsoPuntos;
	}

	public void setIdUsoPuntos(Integer idUsoPuntos) {
		this.idUsoPuntos = idUsoPuntos;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Integer getPuntosUtilizados() {
		return puntosUtilizados;
	}

	public void setPuntosUtilizados(Integer puntosUtilizados) {
		this.puntosUtilizados = puntosUtilizados;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public ConceptoUsoPuntos getConceptoUsoPuntos() {
		return conceptoUsoPuntos;
	}

	public void setIdConceptoUsoPuntos(ConceptoUsoPuntos conceptoUsoPuntos) {
		this.conceptoUsoPuntos = conceptoUsoPuntos;
	}

	public List<DetalleUsoPuntos> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleUsoPuntos> detalles) {
		this.detalles = detalles;
	}

	public void setConceptoUsoPuntos(ConceptoUsoPuntos conceptoUsoPuntos) {
		this.conceptoUsoPuntos = conceptoUsoPuntos;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getSelloTiempo() {
		return selloTiempo;
	}

	public void setSelloTiempo(Date selloTiempo) {
		this.selloTiempo = selloTiempo;
	}
    
    
}
