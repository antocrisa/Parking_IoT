package com.parkinglot.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserInfoDto {
	
	private String name;
	
	private String email;
	
	private String password;
	
	private List<String> role;

}
