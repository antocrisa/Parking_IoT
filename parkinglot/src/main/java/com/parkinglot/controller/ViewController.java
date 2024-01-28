package com.parkinglot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

	@GetMapping({"/home.html", "/"})
	public String showHomePage() {
		return "home.html"; 
	}

	@GetMapping("/dashboard")
//	@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
	public String showValidateFile() {

//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		if (auth.getAuthorities().stream().filter(authority -> authority.getAuthority().equals("ROLE_ADMIN"))
//				.count() > 0) {
//			System.out.println("gran auth " + auth.getAuthorities());
//			return "manager";
//
//		}
		return "dashboard";
	}

	/*
	@GetMapping("/login")
	public String showLoginFile() {
		return "login";
	}*/

	/*
	@GetMapping("/manager")
	public String showManagerFile() {
		return "manager";
	}*/
	/* 
	@GetMapping("/register")
	public String showRegistrationFile() {
		return "register";
	}*/

	/*
	@GetMapping("/details")
	public String showDetailsFile() {
		return "details";
	} */
	
	@GetMapping("/header.html")
	public String showHeaderFile() {
		return "header.html";
	}
	
	@GetMapping("/signup")
	public String showSignupFile() {
		return "signup";
	}
}
