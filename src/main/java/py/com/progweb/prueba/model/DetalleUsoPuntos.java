package py.com.progweb.prueba.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
//import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
//import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="detalle_uso_puntos")
public class DetalleUsoPuntos implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_detalle_uso_puntos")
    @GeneratedValue(generator = "detalleUsoPuntosSec")
    @SequenceGenerator(name = "detalleUsoPuntosSec", sequenceName = "detalle_uso_puntos_sec", allocationSize = 0)
    private Integer idDetalleUsoPuntos;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_uso_puntos")
    private UsoPuntos usoPuntos;
    
    @Basic(optional = false)
    @Column(name = "puntaje_usado")
    private Integer puntajeUsado;
    
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bolsa")
    private BolsaDePuntos bolsa;
    
    public DetalleUsoPuntos() {
    	
    }

	public Integer getIdDetalleUsoPuntos() {
		return idDetalleUsoPuntos;
	}

	public void setIdDetalleUsoPuntos(Integer idDetalleUsoPuntos) {
		this.idDetalleUsoPuntos = idDetalleUsoPuntos;
	}

	public UsoPuntos getUsoPuntos() {
		return usoPuntos;
	}

	public void setUsoPuntos(UsoPuntos idUsoPuntos) {
		this.usoPuntos = idUsoPuntos;
	}

	public Integer getPuntajeUsado() {
		return puntajeUsado;
	}

	public void setPuntajeUsado(Integer puntajeUsado) {
		this.puntajeUsado = puntajeUsado;
	}

	public BolsaDePuntos getIdBolsa() {
		return bolsa;
	}

	public void setBolsa(BolsaDePuntos idBolsa) {
		this.bolsa = idBolsa;
	}
	
}
