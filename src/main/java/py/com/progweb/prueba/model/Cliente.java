package py.com.progweb.prueba.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="cliente")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_cliente", nullable = false)
    @GeneratedValue(generator="clienteSec")
    @SequenceGenerator(name="clienteSec",sequenceName="cliente_sec",allocationSize=0)
    private Integer idCliente;
    
    @Basic(optional = false)
    @Column(name = "nombre",length=50)
    private String nombre;
    
    @Basic(optional = false)
    @Column(name = "apellido",length=50)
    private String apellido;
    
    @Basic(optional = false)
    @Column(name = "nro_documento")
    private Integer nroDocumento;
    
    @Basic(optional = false)
    @Column(name = "tipo_documento",length=50)
    private String tipoDocumento;
    
    @Basic(optional = false)
    @Column(name = "nacionalidad",length=30)
    private String nacionalidad;
    
    @Basic(optional = false)
    @Column(name = "email",length=30)
    private String email;
    
    @Basic(optional = false)
    @Column(name = "telefono",length=30)
    private String telefono;
    
    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT-4")
    private Date fechaNacimiento;
    
    @OneToMany(mappedBy="cliente",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<BolsaDePuntos> listaBolsa;
    
    @JsonIgnore
    public List<BolsaDePuntos> getListaBolsa() {
		return listaBolsa;
	}
	public void setListaBolsa(List<BolsaDePuntos> listaBolsa) {
		this.listaBolsa = listaBolsa;
	}
	public Cliente() {
    	
    }
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
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
	public Integer getNroDocumento() {
		return nroDocumento;
	}
	public void setNroDocumento(Integer nroDocumento) {
		this.nroDocumento = nroDocumento;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

}
