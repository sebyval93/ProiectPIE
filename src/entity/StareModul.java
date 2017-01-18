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
@Table(name = "STAREMODUL")
public class StareModul implements java.io.Serializable {

	
	@Id
	@SequenceGenerator(name = "StModseq", sequenceName = "STAREMODUL_SEQ",allocationSize = 14)
	@GeneratedValue(generator = "StModseq", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private BigDecimal id;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_MODUL", nullable = false)
	private Modul modul;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_SAPTAMANA", nullable = false)
	private Saptamana saptamana;
	
	
	@Column(name = "OPERAT", precision = 22, scale = 0)
	private BigDecimal operat;

	public StareModul() {
	}

	public StareModul(BigDecimal id, Modul modul, Saptamana saptamana, int operat) {
		this.id = id;
		this.modul = modul;
		this.saptamana = saptamana;
		this.operat = new BigDecimal(operat);
	}

	public StareModul(Modul modul, Saptamana saptamana, int operat) {
		this.id = null;
		this.modul = modul;
		this.saptamana = saptamana;
		this.operat = new BigDecimal(operat);
	}
	
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	
	public Modul getModul() {
		return this.modul;
	}

	public void setModul(Modul modul) {
		this.modul = modul;
	}
	
	public Saptamana getSaptamana() {
		return this.saptamana;
	}

	public void setSaptamana(Saptamana saptamana) {
		this.saptamana = saptamana;
	}
	
	

	public BigDecimal getOperat() {
		return this.operat;
	}

	public void setOperat(BigDecimal operat) {
		this.operat = operat;
	}

	@Override
	public String toString() {
		return "StareModul [id=" + id + ", modul=" + modul + ", saptamana=" + saptamana.getDenumire() + ", operat=" + operat + "]";
	}
	
	


}
