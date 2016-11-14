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
@Table(name = "SEMESTRU")
public class Semestru implements java.io.Serializable {

	private BigDecimal id;
	private String numeSem;
	private Set<Saptamana> saptamanas = new HashSet<Saptamana>(0);

	public Semestru() {
	}

	public Semestru(BigDecimal id, String numeSem) {
		this.id = id;
		this.numeSem = numeSem;
	}
	
	public Semestru(String numeSem) {
		this.id = null;
		this.numeSem = numeSem;
	}
	
	public Semestru(BigDecimal id, String numeSem, Set<Saptamana> saptamanas) {
		this.id = id;
		this.numeSem = numeSem;
		this.saptamanas = saptamanas;
	}

	@Id

	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	@SequenceGenerator(name = "semseq", sequenceName = "SEMESTRU_SEQ")
	@GeneratedValue(generator = "semseq", strategy = GenerationType.SEQUENCE)
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@Column(name = "NUME_SEM", nullable = false, length = 20)
	public String getNumeSem() {
		return this.numeSem;
	}

	public void setNumeSem(String numeSem) {
		this.numeSem = numeSem;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "semestru")
	public Set<Saptamana> getSaptamanas() {
		return this.saptamanas;
	}

	public void setSaptamanas(Set<Saptamana> saptamanas) {
		this.saptamanas = saptamanas;
	}

}
