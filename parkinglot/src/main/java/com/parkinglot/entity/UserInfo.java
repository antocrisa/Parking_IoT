package com.parkinglot.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity(name = "user")
@Data
public class UserInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@JsonIgnore
	@Column(name="password")
	private String password;
	
	@Column(name = "current_parkinglot_id")
    private Long currentParkinglotId;

	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
	@JoinTable(name = "user_roles",
		joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
	private List<Role> roles;
}
