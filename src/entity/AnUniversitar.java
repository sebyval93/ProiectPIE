package entity;
//made by DMIRICA

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "AN_UNIVERSITAR")
public class AnUniversitar implements java.io.Serializable {

	private BigDecimal id;
	private BigDecimal an;
	private Set<Grupa> grupas = new HashSet<Grupa>(0);

	public AnUniversitar() {
	}

	public AnUniversitar(BigDecimal id, BigDecimal an) {
		this.id = id;
		this.an = an;
	}
	
	public AnUniversitar(BigDecimal an) {
		this.id = null;
		this.an = an;
	}

	public AnUniversitar(BigDecimal id, BigDecimal an, Set<Grupa> grupas) {
		this.id = id;
		this.an = an;
		this.grupas = grupas;
	}

	@Id
	@SequenceGenerator(name = "anseq", sequenceName = "AN_SEQ")
	@GeneratedValue(generator = "anseq", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@Column(name = "AN", nullable = false, precision = 22, scale = 0)
	public BigDecimal getAn() {
		return this.an;
	}

	public void setAn(BigDecimal an) {
		this.an = an;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "anUniversitar")
	public Set<Grupa> getGrupas() {
		return this.grupas;
	}

	public void setGrupas(Set<Grupa> grupas) {
		this.grupas = grupas;
	}

	@Override
	public String toString() {
		return "AnUniversitar [id=" + id + ", an=" + an + "]";
	}
	
	

}
