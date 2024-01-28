package com.parkinglot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parkinglot.entity.UserInfo;

@Repository
public interface UsersRepository extends JpaRepository<UserInfo,Long>{
	
	boolean existsByEmailIgnoreCase(String email);
	
	UserInfo findByEmail(String email);
	
	UserInfo findByIdAndRolesName(long userId,String roleName);
}
