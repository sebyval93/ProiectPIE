package entity;
// made by DMIRICA

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
@Table(name = "STUDENT")
public class Student implements java.io.Serializable {

	private BigDecimal id;
	private Subgrupa subgrupa;
	private String nume;
	private Set<Prezenta> prezentas = new HashSet<Prezenta>(0);

	public Student() {
	}

	public Student(BigDecimal id, Subgrupa subgrupa, String nume) {
		this.id = id;
		this.subgrupa = subgrupa;
		this.nume = nume;
	}
	
	public Student(Subgrupa subgrupa, String nume) {
		this.id = null;
		this.subgrupa = subgrupa;
		this.nume = nume;
	}

	public Student(BigDecimal id, Subgrupa subgrupa, String nume, Set<Prezenta> prezentas) {
		this.id = id;
		this.subgrupa = subgrupa;
		this.nume = nume;
		this.prezentas = prezentas;
	}

	@Id
	@SequenceGenerator(name = "studseq", sequenceName = "STUDENT_SEQ")
	@GeneratedValue(generator = "studseq", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SUBGRUPA", nullable = false)
	public Subgrupa getSubgrupa() {
		return this.subgrupa;
	}

	public void setSubgrupa(Subgrupa subgrupa) {
		this.subgrupa = subgrupa;
	}

	@Column(name = "NUME", nullable = false, length = 60)
	public String getNume() {
		return this.nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
	public Set<Prezenta> getPrezentas() {
		return this.prezentas;
	}

	public void setPrezentas(Set<Prezenta> prezentas) {
		this.prezentas = prezentas;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", subgrupa=" + subgrupa + ", nume= " + nume + "]";
	}
	
	
	
	

}
