package entity;
//made by DMIRICA

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PREZENTA")
public class Prezenta implements java.io.Serializable {

	private BigDecimal id;
	private Modul modul;
	private Saptamana saptamana;
	private Student student;
	private BigDecimal prezent;

	public Prezenta() {
	}

	public Prezenta(BigDecimal id, Modul modul, Saptamana saptamana, Student student) {
		this.id = id;
		this.modul = modul;
		this.saptamana = saptamana;
		this.student = student;
	}

	public Prezenta(BigDecimal id, Modul modul, Saptamana saptamana, Student student, BigDecimal prezent) {
		this.id = id;
		this.modul = modul;
		this.saptamana = saptamana;
		this.student = student;
		this.prezent = prezent;
	}
	
	public Prezenta(Modul modul, Saptamana saptamana, Student student, int prezent) {
		this.id = null;
		this.modul = modul;
		this.saptamana = saptamana;
		this.student = student;
		this.prezent = new BigDecimal(prezent);
	}

	@Id
	@SequenceGenerator(name = "prezseq", sequenceName = "PREZENTA_SEQ",allocationSize = 1)
	@GeneratedValue(generator = "prezseq", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MODUL", nullable = false)
	public Modul getModul() {
		return this.modul;
	}

	public void setModul(Modul modul) {
		this.modul = modul;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SAPTAMANA", nullable = false)
	public Saptamana getSaptamana() {
		return this.saptamana;
	}

	public void setSaptamana(Saptamana saptamana) {
		this.saptamana = saptamana;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_STUDENT", nullable = false)
	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Column(name = "PREZENT", precision = 22, scale = 0)
	public BigDecimal getPrezent() {
		return this.prezent;
	}

	public void setPrezent(BigDecimal prezent) {
		this.prezent = prezent;
	}

}
