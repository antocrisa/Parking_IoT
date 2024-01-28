package com.parkinglot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglot.dto.UserInfoDto;
import com.parkinglot.entity.UserInfo;
import com.parkinglot.jwtResponse.JwtResponse;
import com.parkinglot.service.UsersService;

@RestController
@RequestMapping("/api/users")
public class UsersController {

	@Autowired
	UsersService usersService;

	@PostMapping("/signup")
	public ResponseEntity<UserInfo> signUp(@RequestBody UserInfoDto dto) {
		UserInfo signUp = usersService.signUp(dto);
		return new ResponseEntity<UserInfo>(signUp, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody UserInfoDto dto) {
		JwtResponse login = usersService.login(dto);
		return new ResponseEntity<JwtResponse>(login, HttpStatus.OK);
	}
	
	


}
