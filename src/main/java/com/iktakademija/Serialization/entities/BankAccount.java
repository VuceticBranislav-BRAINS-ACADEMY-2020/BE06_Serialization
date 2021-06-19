package com.iktakademija.Serialization.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktakademija.Serialization.security.Views;

@Entity
@Table(name = "account")
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class BankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.Private.class)
	private Integer id;

	@Column(nullable = false)
	@JsonView(Views.Private.class)
	private String iban;

	@JsonBackReference("JoinAccount")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private UserEntity user;

	@Column
	private Double balance;

	// Valuta, stanje, aktivan
	@Version
	private Integer version;

	public BankAccount() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

}
