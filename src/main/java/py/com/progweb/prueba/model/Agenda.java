package py.com.progweb.prueba.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="agenda")
public class Agenda implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_agenda")
    @GeneratedValue(generator="agendaSec")
    @SequenceGenerator(name="agendaSec",sequenceName="agenda_sec",allocationSize=0)
    private Integer idAgenda;
    @Basic(optional = false)
    @Column(name = "actividad",length=200)
    private String actividad;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    @ManyToOne(optional = false)
    private Persona idPersona;
    
    public Agenda () {
    	
    }

	public Integer getIdAgenda() {
		return idAgenda;
	}

	public void setIdAgenda(Integer idAgenda) {
		this.idAgenda = idAgenda;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Persona getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Persona idPersona) {
		this.idPersona = idPersona;
	}
	@Override
    public Agenda clone() {
    		Agenda a=new Agenda();
    		a.setActividad(this.actividad);
    		a.setFecha(this.getFecha());
    		a.setIdAgenda(this.idAgenda);
    		return a;
    }
}
