package com.parkinglot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parkinglot.entity.Parkinglot;

@Repository
public interface ParkinglotRepository extends JpaRepository<Parkinglot, Long>{
	
	Parkinglot findByIdAndAddedById(long parkId,long userId);
	
	List<Parkinglot> findAllByCurrentState(String status);
	
	Parkinglot findByIdAndCurrentState(long parkId,String status);
	
	
}
