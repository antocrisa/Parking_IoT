package com.parkinglot.security.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.parkinglot.entity.UserInfo;
import com.parkinglot.exception.NotFoundException;
import com.parkinglot.repository.UsersRepository;


//LOGIN
//UserDetailsService utilizza la mail(username) per verificare l'utente associando la pass
//implementa l'interfaccia UserDetailsService di Spring Security
//che è responsabile di recuperare le informazioni dell'utente durante il processo di autenticazione 
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
  @Autowired
  UsersRepository usersRepository;

  
  @Transactional
  public UserDetails loadUserByUsername(String email) throws NotFoundException {
	   	
	 UserInfo users = usersRepository.findByEmail(email);
	  
	 if(users==null) {
		 throw new NotFoundException("username not found");
	 	}
      return UserDetailsImpl.build(users);
  }

}
