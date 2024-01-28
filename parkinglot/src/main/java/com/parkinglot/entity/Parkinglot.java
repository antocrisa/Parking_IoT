package com.parkinglot.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity (name = "parkinglot")
public class Parkinglot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String quarter;
	
	private String via;
	
	private int totalPlaces;
	
	private int freeSeats;
	
	private String currentState;
	
	@OneToOne
	private UserInfo addedBy;

	@ManyToMany
    @JoinTable(
            name = "parkinglot_users",
            joinColumns = @JoinColumn(name = "parkinglot_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserInfo> usersInside = new HashSet<>();
}
