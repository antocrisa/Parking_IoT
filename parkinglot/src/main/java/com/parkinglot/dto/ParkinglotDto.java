package com.parkinglot.dto;


import lombok.Data;

@Data
public class ParkinglotDto {
	
	private String quarter;
	
	private String via;
	
	private int totalPlaces;
	
	private int freeSeats;
	
	private String currentState;
	
	private long userId;
}
