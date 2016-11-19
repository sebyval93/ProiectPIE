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
@Table(name = "PROFESOR")
public class Profesor implements java.io.Serializable {

	private BigDecimal id;
	private String nume;
	private Set<Modul> moduls = new HashSet<Modul>(0);

	public Profesor() {
	}

	public Profesor(BigDecimal id) {
		this.id = id;
	}

	public Profesor(BigDecimal id, String nume, Set<Modul> moduls) {
		this.id = id;
		this.nume = nume;
		this.moduls = moduls;
	}
	
	public Profesor(String nume) {
		this.id = null;
		this.nume = nume;
	}

	@Id
	@SequenceGenerator(name = "profseq", sequenceName = "PROFESOR_SEQ",allocationSize = 1)
	@GeneratedValue(generator = "profseq", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@Column(name = "NUME", length = 60)
	public String getNume() {
		return this.nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "profesor")
	public Set<Modul> getModuls() {
		return this.moduls;
	}

	public void setModuls(Set<Modul> moduls) {
		this.moduls = moduls;
	}

}
