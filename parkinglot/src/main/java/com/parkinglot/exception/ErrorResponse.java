package com.parkinglot.exception;

import com.parkinglot.exception.ErrorResponse;

import lombok.Data;

@Data
public class ErrorResponse {
	
	private String status;
	private String message;
	private long timeStamp;
	
	
}
