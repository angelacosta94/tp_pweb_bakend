package py.com.progweb.prueba.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "regla_asignacion")
public class ReglaAsignacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id_regla_asignacion", nullable = false)
    @GeneratedValue(generator = "reglaAsignacionSec")
    @SequenceGenerator(name = "reglaAsignacionSec", sequenceName = "regla_asignacion_sec", allocationSize = 0)
    private Integer idReglaAsignacion;

    @Basic(optional = false)
    @Column(name = "desde")
    private Integer desde;

    @Basic(optional = false)
    @Column(name = "hasta")
    private Integer hasta;

    @Basic(optional = false)
    @Column(name = "valor")
    private Integer valor;

	public Integer getIdReglaAsignacion() {
		return idReglaAsignacion;
	}

	public void setIdReglaAsignacion(Integer idReglaAsignacion) {
		this.idReglaAsignacion = idReglaAsignacion;
	}

	public Integer getDesde() {
		return desde;
	}

	public void setDesde(Integer desde) {
		this.desde = desde;
	}

	public Integer getHasta() {
		return hasta;
	}

	public void setHasta(Integer hasta) {
		this.hasta = hasta;
	}

	public Integer getValor() {
		return valor;
	}

	public void setValor(Integer valor) {
		this.valor = valor;
	}

}
