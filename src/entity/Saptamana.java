package entity;
//made by DMIRICA

import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "SAPTAMANA")
public class Saptamana implements java.io.Serializable {

	@Id
	@SequenceGenerator(name = "saptseq", sequenceName = "SAPTAMANA_SEQ",allocationSize = 1)
	@GeneratedValue(generator = "saptseq", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private BigDecimal id;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SEM", nullable = false)
	private Semestru semestru;
	
	@Column(name = "DENUMIRE", nullable = false, length = 20)
	private String denumire;

	@Temporal(TemporalType.DATE)
	@Column(name = "STARTDATE", length = 7)
	private Date startdate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "ENDDATE", length = 7)
	private Date enddate;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "saptamana")
	private Set<Prezenta> prezentas = new HashSet<Prezenta>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "saptamana")
	private Set<StareModul> stats = new HashSet<StareModul>(0);

	public Saptamana() {
	}

	public Saptamana(BigDecimal id, Semestru semestru, String denumire) {
		this.id = id;
		this.semestru = semestru;
		this.denumire = denumire;
	}
	
	public Saptamana(Semestru semestru, String denumire, Date startdate, Date enddate){
		this.id = null;
		this.semestru = semestru;
		this.denumire = denumire;
		this.startdate = startdate;
		this.enddate = enddate;
	}

	public Saptamana(BigDecimal id, Semestru semestru, String denumire, Date startdate, Date enddate,
			Set<Prezenta> prezentas) {
		this.id = id;
		this.semestru = semestru;
		this.denumire = denumire;
		this.startdate = startdate;
		this.enddate = enddate;
		this.prezentas = prezentas;
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

	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Set<Prezenta> getPrezentas() {
		return this.prezentas;
	}

	public void setStats(Set<StareModul> stats) {
		this.stats = stats;
	}
	
	public Set<StareModul> getStats() {
		return this.stats;
	}

	public void setPrezentas(Set<Prezenta> prezentas) {
		this.prezentas = prezentas;
	}

	@Override
	public String toString() {
		return "week "+this.getId()+"\nFirst day "+this.getStartdate()+" \nLast day "+this.getEnddate();
	}
	
	
}
