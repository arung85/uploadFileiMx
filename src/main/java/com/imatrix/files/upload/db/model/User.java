package com.imatrix.files.upload.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "tbluser", uniqueConstraints = {@UniqueConstraint(columnNames = "Username"),
        @UniqueConstraint(columnNames = "Level")})
public class User {
	@Id
	@GeneratedValue
	private Long id;
	@NonNull
	@Column(length=145)
	private String Username;
	@Column(length=145)
	private String Password;
	@Column(length=45)
	private String Level;
	public User() {
	}
	public User(String username, String password, String level) {
		super();
		Username = username;
		Password = password;
		Level = level;
	}

}
