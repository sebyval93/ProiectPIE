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
@Table(name = "DISCIPLINA")
public class Disciplina implements java.io.Serializable {

	@Id
	@SequenceGenerator(name = "discipseq", sequenceName = "DISCIPLINA_SEQ",allocationSize = 1)
	@GeneratedValue(generator = "discipseq", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private BigDecimal id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SEMESTRU")
	private Semestru semestru;
	
	@Column(name = "DENUMIRE", length = 60)
	private String denumire;
	
	@Column(name = "AN", precision = 22, scale = 0)
	private BigDecimal an;
	
	@Column(name = "ORECURS", precision = 22, scale = 0)
	private BigDecimal orecurs;
	
	@Column(name = "ORELAB", precision = 22, scale = 0)
	private BigDecimal orelab;
	
	@Column(name = "ORESEMINAR", precision = 22, scale = 0)
	private BigDecimal oreseminar;
	
	@Column(name = "OREPROIECT", precision = 22, scale = 0)
	private BigDecimal oreproiect;
	
	@Column(name = "NUME_SCURT", length = 10)
	private String numeScurt;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "disciplina")
	private Set<Modul> moduls = new HashSet<Modul>(0);

	public Disciplina() {
	}

	public Disciplina(BigDecimal id) {
		this.id = id;
	}

	public Disciplina(BigDecimal id, Semestru semestru, String denumire, BigDecimal an, BigDecimal orecurs,
			BigDecimal orelab, BigDecimal oreseminar, BigDecimal oreproiect, String numeScurt, Set<Modul> moduls) {
		this.id = id;
		this.semestru = semestru;
		this.denumire = denumire;
		this.an = an;
		this.orecurs = orecurs;
		this.orelab = orelab;
		this.oreseminar = oreseminar;
		this.oreproiect = oreproiect;
		this.numeScurt = numeScurt;
		this.moduls = moduls;
	}
	
	public Disciplina(String denumire,Semestru semestru, BigDecimal an, BigDecimal orecurs, BigDecimal orelab,
			BigDecimal oreseminar, BigDecimal oreproiect, String numeScurt) {
		this.id = null;
		this.denumire = denumire;
		this.semestru = semestru;
		this.an = an;
		this.orecurs = orecurs;
		this.orelab = orelab;
		this.oreseminar = oreseminar;
		this.oreproiect = oreproiect;
		this.numeScurt = numeScurt;
	}

	
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public Semestru getSemestru() {
		return this.semestru;
	}

	public void setSemestru(Semestru semestru) {
		this.semestru = semestru;
	}

	public String getDenumire() {
		return this.denumire;
	}

	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}

	public BigDecimal getAn() {
		return this.an;
	}

	public void setAn(BigDecimal an) {
		this.an = an;
	}

	public BigDecimal getOrecurs() {
		return this.orecurs;
	}

	public void setOrecurs(BigDecimal orecurs) {
		this.orecurs = orecurs;
	}

	public BigDecimal getOrelab() {
		return this.orelab;
	}

	public void setOrelab(BigDecimal orelab) {
		this.orelab = orelab;
	}

	public BigDecimal getOreseminar() {
		return this.oreseminar;
	}

	public void setOreseminar(BigDecimal oreseminar) {
		this.oreseminar = oreseminar;
	}

	public BigDecimal getOreproiect() {
		return this.oreproiect;
	}

	public void setOreproiect(BigDecimal oreproiect) {
		this.oreproiect = oreproiect;
	}

	public String getNumeScurt() {
		return this.numeScurt;
	}

	public void setNumeScurt(String numeScurt) {
		this.numeScurt = numeScurt;
	}

	public Set<Modul> getModuls() {
		return this.moduls;
	}

	public void setModuls(Set<Modul> moduls) {
		this.moduls = moduls;
	}
	
	@Override
	public String toString() {
		return "Disciplina [id= " + id + ", denumire= " + denumire + ", an= " + an + ", orecurs= " + orecurs + ", orelab= "
				+ orelab + ", oreseminar= " + oreseminar + ", oreproiect= " + oreproiect + ", numeScurt= " + numeScurt
				+ "]";
	}
}
