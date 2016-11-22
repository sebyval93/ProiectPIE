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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "GRUPA")
public class Grupa implements java.io.Serializable {

	

	private BigDecimal id;
	private AnUniversitar anUniversitar;
	private String nume;
	private Set<Subgrupa> subgrupas = new HashSet<Subgrupa>(0);

	public Grupa() {
	}

	public Grupa(BigDecimal id, AnUniversitar anUniversitar) {
		this.id = id;
		this.anUniversitar = anUniversitar;
	}
	
	public Grupa(AnUniversitar anUniversitar,String nume) {
		this.id = null;
		this.nume = nume;
		this.anUniversitar = anUniversitar;
	}
	
	public Grupa(BigDecimal id, AnUniversitar anUniversitar, String nume, Set<Subgrupa> subgrupas) {
		this.id = id;
		this.anUniversitar = anUniversitar;
		this.nume = nume;
		this.subgrupas = subgrupas;
	}

	@Id
	@SequenceGenerator(name = "grupaseq", sequenceName = "GRUPA_SEQ",allocationSize = 1)
	@GeneratedValue(generator = "grupaseq", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_AN", nullable = false)
	public AnUniversitar getAnUniversitar() {
		return this.anUniversitar;
	}

	public void setAnUniversitar(AnUniversitar anUniversitar) {
		this.anUniversitar = anUniversitar;
	}

	@Column(name = "NUME", length = 20)
	public String getNume() {
		return this.nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "grupa")
	public Set<Subgrupa> getSubgrupas() {
		return this.subgrupas;
	}

	public void setSubgrupas(Set<Subgrupa> subgrupas) {
		this.subgrupas = subgrupas;
	}

	@Override
	public String toString() {
		return nume;
	}
	
	

}
