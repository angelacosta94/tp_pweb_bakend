package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "vencimiento_puntos")
public class VencimientoDePuntos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_vencimiento_puntos", nullable = false)
    @GeneratedValue(generator = "vencimientoPuntosSec")
    @SequenceGenerator(name = "vencimientoPuntosSec", sequenceName = "vencimiento_puntos_sec", allocationSize = 0)
    private Integer idVencimiento;

    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @Column(name = "inicio_validez")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT-4")
    private Date inicioValidez;

    @Basic(optional = false)
    @Temporal(TemporalType.DATE)
    @Column(name = "fin_validez")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT-4")
    private Date finValidez;

    @Basic(optional = false)
    @Column(name = "dias_duracion")
    private Integer diasDuracion;

    public Integer getIdVencimiento() {
        return idVencimiento;
    }

    public void setIdVencimiento(Integer idVencimiento) {
        this.idVencimiento = idVencimiento;
    }

    public Date getInicioValidez() {
        return inicioValidez;
    }

    public void setInicioValidez(Date inicioValidez) {
        this.inicioValidez = inicioValidez;
    }

    public Date getFinValidez() {
        return finValidez;
    }

    public void setFinValidez(Date finValidez) {
        this.finValidez = finValidez;
    }

    public Integer getDiasDuracion() {
        return diasDuracion;
    }

    public void setDiasDuracion(Integer diasDuracion) {
        this.diasDuracion = diasDuracion;
    }
}
