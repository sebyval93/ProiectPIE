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
@Table(name = "SUBGRUPA")
public class Subgrupa implements java.io.Serializable {

	@Id
	@SequenceGenerator(name = "subgrseq", sequenceName = "SUBGRUPA_SEQ",allocationSize = 1)
	@GeneratedValue(generator = "subgrseq", strategy = GenerationType.SEQUENCE)	
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private BigDecimal id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_GRUPA", nullable = false)
	private Grupa grupa;
	
	@Column(name = "NUME", nullable = false, length = 20)
	private String nume;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subgrupa")
	private Set<Student> students = new HashSet<Student>(0);

	public Subgrupa() {
	}

	public Subgrupa(BigDecimal id, Grupa grupa, String nume) {
		this.id = id;
		this.grupa = grupa;
		this.nume = nume;
	}
	
	public Subgrupa( Grupa grupa, String nume) {
		this.id = null;
		this.grupa = grupa;
		this.nume = nume;
	}

	public Subgrupa(BigDecimal id, Grupa grupa, String nume, Set<Student> students) {
		this.id = id;
		this.grupa = grupa;
		this.nume = nume;
		this.students = students;
	}

	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public Grupa getGrupa() {
		return this.grupa;
	}

	public void setGrupa(Grupa grupa) {
		this.grupa = grupa;
	}

	public String getNume() {
		return this.nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public Set<Student> getStudents() {
		return this.students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return nume;
	}
	
	
	

}
