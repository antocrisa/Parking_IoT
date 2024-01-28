package com.parkinglot.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parkinglot.entity.Role;
import com.parkinglot.entity.UserInfo;

//CONTROLLA LE AUTORIZZAZIONI UTENTE
//implementa l'interfaccia UserDetails di Spring Security che fornisce
//una rappresentazione dettagliata degli utenti nel sistema (ruoli, informazioni personali e autorità)
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private List<Role> role;
	
	private String name;
	
	private String email;

	@JsonIgnore
	private String password;
	
	
	private Collection<? extends GrantedAuthority> authorities;

	
	public UserDetailsImpl(Long id, List<Role> list, String name, String email, String password) {
		super();
		this.id = id;
		this.role = list;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public static UserDetailsImpl build(UserInfo user) {
		return new UserDetailsImpl(user.getId(),user.getRoles(),user.getName(),user.getEmail(),user.getPassword());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}
	

	public List<Role> getRole() {
		return role;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public void setRole(List<Role> role) {
		this.role = role;
	}

	public void setEmailId(String email) {
		this.email = email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}


	

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}

	
	
}
