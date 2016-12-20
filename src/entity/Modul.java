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
@Table(name = "MODUL")
public class Modul implements java.io.Serializable {

	private BigDecimal id;
	private Disciplina disciplina;
	private Profesor profesor;
	private String activitate;
	private String participanti;
	private BigDecimal interval;
	private BigDecimal operat;
	private Set<Prezenta> prezentas = new HashSet<Prezenta>(0);

	public Modul() {
	}

	public Modul(BigDecimal id, Disciplina disciplina, Profesor profesor, String activitate, String participanti,
			BigDecimal interval, BigDecimal operat) {
		this.id = id;
		this.disciplina = disciplina;
		this.profesor = profesor;
		this.activitate = activitate;
		this.participanti = participanti;
		this.interval = interval;
		this.operat = operat;
	}

	public Modul(BigDecimal id, Disciplina disciplina, Profesor profesor, String activitate, String participanti,
			BigDecimal interval, BigDecimal operat, Set<Prezenta> prezentas) {
		this.id = id;
		this.disciplina = disciplina;
		this.profesor = profesor;
		this.activitate = activitate;
		this.participanti = participanti;
		this.interval = interval;
		this.operat = operat;
		this.prezentas = prezentas;
	}
	
	public Modul(Disciplina disciplina, Profesor profesor, String activitate, String participanti,
			BigDecimal interval,BigDecimal operat) {
		this.id = null;
		this.disciplina = disciplina;
		this.profesor = profesor;
		this.activitate = activitate;
		this.participanti = participanti;
		this.interval = interval;
		this.operat = operat;
	}

	@Id
	@SequenceGenerator(name = "modseq", sequenceName = "MODUL_SEQ",allocationSize = 1)
	@GeneratedValue(generator = "modseq", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DISCIPLINA", nullable = false)
	public Disciplina getDisciplina() {
		return this.disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PROFESOR", nullable = false)
	public Profesor getProfesor() {
		return this.profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	@Column(name = "ACTIVITATE", nullable = false, length = 20)
	public String getActivitate() {
		return this.activitate;
	}

	public void setActivitate(String activitate) {
		this.activitate = activitate;
	}

	@Column(name = "PARTICIPANTI", nullable = false, length = 20)
	public String getParticipanti() {
		return this.participanti;
	}

	public void setParticipanti(String participanti) {
		this.participanti = participanti;
	}

	@Column(name = "INTERVAL", nullable = false, precision = 22, scale = 0)
	public BigDecimal getInterval() {
		return this.interval;
	}

	public void setInterval(BigDecimal interval) {
		this.interval = interval;
	}

	@Column(name = "OPERAT", nullable = false, precision = 22, scale = 0)
	public BigDecimal getOperat() {
		return this.operat;
	}

	public void setOperat(BigDecimal operat) {
		this.operat = operat;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modul")
	public Set<Prezenta> getPrezentas() {
		return this.prezentas;
	}

	public void setPrezentas(Set<Prezenta> prezentas) {
		this.prezentas = prezentas;
	}
	
	@Override
	public String toString() {
		return "Modul [id=" + id + ", disciplina=" + disciplina + ", profesor=" + profesor + ", activitate="
				+ activitate + ", participanti=" + participanti + ", interval=" + interval + "]";
	}

}
