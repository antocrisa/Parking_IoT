package com.example.mosquittoclient;

import org.springframework.stereotype.Component;

@Component
abstract public class Device {
	protected String topicprefix = "parkinglot/";
	
	public abstract void subscribe();

}
