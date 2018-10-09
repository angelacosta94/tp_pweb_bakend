package py.com.progweb.prueba.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="persona")
public class Persona implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_persona")
    @GeneratedValue(generator="personaSec")
    @SequenceGenerator(name="personaSec",sequenceName="persona_sec",allocationSize=0)
    private Integer idPersona;
    @Basic(optional = false)
    @Column(name = "nombre",length=50)
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellido",length=50)
    private String apellido;
    @OneToMany(mappedBy="idPersona")
    @JsonIgnore
    private List<Agenda> listaAgenda;
    @Transient
    private List<Agenda> listaAgendaTransient;
    public Persona() {
    	
    }
	public Integer getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
    public List<Agenda> getListaAgenda() {
		return listaAgenda;
	}
    public void setListaAgenda(List<Agenda> listaAgenda) {
		this.listaAgenda = listaAgenda;
	}
    public List<Agenda> getListaAgendaTransient() {
		return listaAgendaTransient;
	}
    public void setListaAgendaTransient(List<Agenda> listaAgendaTransient) {
		this.listaAgendaTransient = listaAgendaTransient;
	}
}

