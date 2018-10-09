package py.com.progweb.prueba.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="concepto_uso_puntos")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ConceptoUsoPuntos  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator="conceptoUsoPuntosSec")
    @SequenceGenerator(name="conceptoUsoPuntosSec",sequenceName="concepto_uso_puntos_sec",allocationSize=0)
    @Column(name = "id_concepto_uso_puntos", nullable = false)
    private Integer idConceptoUsoPuntos;

	@Column(name = "descripcion", length = 50)
	private String descripcion;

	@Column(name = "puntos_requeridos")
	private Integer puntosRequeridos;

    @OneToMany(mappedBy="conceptoUsoPuntos",fetch = FetchType.LAZY)
    @JsonIgnore
    private  List <UsoPuntos> usoPuntosPorConcepto;

    public Integer getIdConceptoUsoPuntos() {
        return idConceptoUsoPuntos;
    }

    public void setIdConceptoUsoPuntos(Integer idConceptoUsoPuntos) {
        this.idConceptoUsoPuntos = idConceptoUsoPuntos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPuntosRequeridos() {
        return puntosRequeridos;
    }

    public void setPuntosRequeridos(Integer puntosRequeridos) {
        this.puntosRequeridos = puntosRequeridos;
    }

    public List<UsoPuntos> getUsoPuntosPorConcepto() {
        return usoPuntosPorConcepto;
    }

    public void setUsoPuntosPorConcepto(List<UsoPuntos> usoPuntosPorConcepto) {
        this.usoPuntosPorConcepto = usoPuntosPorConcepto;
    }
}
