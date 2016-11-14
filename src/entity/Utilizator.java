package entity;
//made by DMIRICA

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "UTILIZATOR")
public class Utilizator implements java.io.Serializable {

	private BigDecimal id;
	private String username;
	private String password;

	public Utilizator() {
	}

	public Utilizator(BigDecimal id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
	public Utilizator(String username, String password) {
		this.id = null;
		this.username = username;
		this.password = password;
	}

	@Id
	@SequenceGenerator(name = "utilseq", sequenceName = "UTILIZATOR_SEQ")
	@GeneratedValue(generator = "utilseq", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	@Column(name = "USERNAME", nullable = false, length = 20)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD", nullable = false, length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
