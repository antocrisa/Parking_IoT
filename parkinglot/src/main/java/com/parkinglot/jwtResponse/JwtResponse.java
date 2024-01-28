package com.parkinglot.jwtResponse;

import java.util.List;

import com.parkinglot.entity.Role;

public class JwtResponse {
	
	
	private String token;
	
	private long id;
	
	private List<Role> role;
	
	private String name;
	
	private String email;
	
	
	public JwtResponse(String token, long id, List<Role> list, String name, String email) {
		super();
		this.token = token;
		this.id = id;
		this.role = list;
		this.name = name;
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Role> getRole() {
		return role;
	}

	public void setRole(List<Role> role) {
		this.role = role;
	}
	
	

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
