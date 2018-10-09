package py.com.progweb.prueba.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

@Entity
@Table(name = "bolsa_de_puntos")

public class BolsaDePuntos {
    @Id
    @Basic(optional = false)
    @Column(name = "id_bolsa_de_puntos")
    @GeneratedValue(generator = "bolsaSec")
    @SequenceGenerator(name = "bolsaSec", sequenceName = "bolsa_de_puntos_sec", allocationSize = 0)
    private Integer idBolsaPuntos;
    @Basic(optional = false)
    @Column(name = "fecha_asignacion_puntaje")
    @Temporal(TemporalType.DATE)
    private Date fechaAsignacion;
    @Basic(optional = false)
    @Column(name = "fecha_caducidad_puntaje")
    @Temporal(TemporalType.DATE)
    private Date fechaCaducidad;
    @Basic(optional = false)
    @Column(name = "puntaje_asignado")
    private Integer puntajeAsignado;
    @Basic(optional = false)
    @Column(name = "puntaje_utilizado")
    private Integer puntajeUtilizado;
    @Basic(optional = false)
    @Column(name = "saldo_puntos")
    private Integer saldoPuntos;
    @Basic(optional = false)
    @Column(name = "monto_operacion")
    private Integer montoOperacion;
    @Column(name = "usuario", length = 50)
	private String usuario;
    @Column(name = "sello_tiempo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date selloTiempo;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Cliente cliente;

    public Integer getIdBolsaPuntos() {
        return idBolsaPuntos;
    }

    public void setIdBolsaPuntos(Integer idBolsaPuntos) {
        this.idBolsaPuntos = idBolsaPuntos;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public Integer getPuntajeAsignado() {
        return puntajeAsignado;
    }

    public void setPuntajeAsignado(Integer puntajeAsignado) {
        this.puntajeAsignado = puntajeAsignado;
    }

    public Integer getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(Integer puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }

    public Integer getSaldoPuntos() {
        return saldoPuntos;
    }

    public void setSaldoPuntos(Integer saldoPuntos) {
        this.saldoPuntos = saldoPuntos;
    }

    public Integer getMontoOperacion() {
        return montoOperacion;
    }

    public void setMontoOperacion(Integer montoOperacion) {
        this.montoOperacion = montoOperacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
