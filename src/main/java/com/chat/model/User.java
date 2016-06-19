package com.chat.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * @author Shreya Viramgama
 *
 */
@Entity
@Table(name = "USERS")
public class User implements Serializable{
	private static final long serialVersionUID = 1L;


	@Id
	@Column(name="USERNAME")
	private String username;

	@JsonIgnore
	@Column(name="PASSWORD")
	private String password;

	@NotNull
	@Column(name="FIRST_NAME")
	private String firstName;

	@NotNull
	@Column(name="LAST_NAME")
	private String lastName;


	public User() {
	}

	public User(String username, String firstName, String lastNmae) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastNmae;
		this.password = "";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}