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

	@Id
	@SequenceGenerator(name = "modseq", sequenceName = "MODUL_SEQ",allocationSize = 1)
	@GeneratedValue(generator = "modseq", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private BigDecimal id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DISCIPLINA", nullable = false)
	private Disciplina disciplina;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PROFESOR", nullable = false)
	private Profesor profesor;
	
	@Column(name = "ACTIVITATE", nullable = false, length = 20)
	private String activitate;
	
	@Column(name = "PARTICIPANTI", nullable = false, length = 20)
	private String participanti;
	
	@Column(name = "INTERVAL", nullable = false, precision = 22, scale = 0)
	private BigDecimal interval;
	
	@Column(name = "OPERAT", nullable = false, precision = 22, scale = 0)
	private BigDecimal operat;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "modul")
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

	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public Disciplina getDisciplina() {
		return this.disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Profesor getProfesor() {
		return this.profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public String getActivitate() {
		return this.activitate;
	}

	public void setActivitate(String activitate) {
		this.activitate = activitate;
	}

	public String getParticipanti() {
		return this.participanti;
	}

	public void setParticipanti(String participanti) {
		this.participanti = participanti;
	}

	public BigDecimal getInterval() {
		return this.interval;
	}

	public void setInterval(BigDecimal interval) {
		this.interval = interval;
	}

	public BigDecimal getOperat() {
		return this.operat;
	}

	public void setOperat(BigDecimal operat) {
		this.operat = operat;
	}

	public Set<Prezenta> getPrezentas() {
		return this.prezentas;
	}

	public void setPrezentas(Set<Prezenta> prezentas) {
		this.prezentas = prezentas;
	}
	
	@Override
	public String toString() {
		return "Modul [id=" + id + ", disciplina=" + disciplina.getDenumire() + ", profesor=" + profesor.getNume() + ", activitate="
				+ activitate + ", participanti=" + participanti + ", interval=" + interval + "]";
	}

}
