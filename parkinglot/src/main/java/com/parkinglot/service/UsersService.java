package com.parkinglot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.parkinglot.dto.UserInfoDto;
import com.parkinglot.entity.Role;
import com.parkinglot.entity.UserInfo;
import com.parkinglot.exception.AlreadyExistsException;
import com.parkinglot.exception.NotFoundException;
import com.parkinglot.jwtResponse.JwtResponse;
import com.parkinglot.repository.RoleRepository;
import com.parkinglot.repository.UsersRepository;
import com.parkinglot.security.JwtUtils;
import com.parkinglot.security.service.UserDetailsImpl;

@Service
public class UsersService {

	@Autowired
	UsersRepository usersRepo;

	@Autowired
	RoleRepository roleRepo;

	//Spring secutiry
	@Autowired
	AuthenticationManager authenticationManager;

	//Spring secutiry
	@Autowired
	PasswordEncoder passwordEncoder;

	//JWT 
	@Autowired
	JwtUtils jwtUtils;


	//SignUp save(new user) in Repository
	public UserInfo signUp(UserInfoDto dto) {
		try {

			boolean isEmail = usersRepo.existsByEmailIgnoreCase(dto.getEmail());

			if (isEmail) {
				throw new AlreadyExistsException("Email id is already exixt");
			} else {

				List<Role> role = roleRepo.findByNameIn(dto.getRole());

				if (role.isEmpty()) {
					throw new NotFoundException("Role not found");
				} else {

					UserInfo user = new UserInfo();
					user.setName(dto.getName());
					user.setEmail(dto.getEmail());
					user.setPassword(passwordEncoder.encode(dto.getPassword()));

					user.setRoles(role);

					return usersRepo.save(user);
				}

			}
		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	//Autentica l'utente utilizzando l'AuthenticationManager di Spring Security
	public JwtResponse login(UserInfoDto dto) {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

			String jwt = jwtUtils.generateJwtToken(authentication);

			return new JwtResponse(jwt, userDetails.getId(), userDetails.getRole(), userDetails.getName(),
					userDetails.getEmail());
		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
	}
}
