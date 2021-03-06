package entity;
//made by DMIRICA

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;

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
import Utils.*;
import oracle.net.ano.EncryptionService;

@Entity
@Table(name = "UTILIZATOR")
public class Utilizator implements java.io.Serializable {

	@Id
	@SequenceGenerator(name = "utilseq", sequenceName = "UTILIZATOR_SEQ",allocationSize = 1)
	@GeneratedValue(generator = "utilseq", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
	private BigDecimal id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PROFESOR", nullable = false)
	private Profesor profesor;
	
	@Column(name = "USERNAME", nullable = false, length = 20)
	private String username;
	
	@Column(name = "PASSWORD", nullable = true, length = 100)
	private String password;

	public Utilizator() {
	}

	public Utilizator(BigDecimal id, Profesor profesor, String username, String password) {
		this.id = id;
		this.profesor = profesor;
		this.username = username;
		this.password = password;
	}
	
	public Utilizator(String username, String password,Profesor profesor) {
		this.id = null;
		this.profesor = profesor;
		this.username = username;	
		this.password = password;
	}

	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}
	
	public Profesor getProfesor() {
		return this.profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
