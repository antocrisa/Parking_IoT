package com.example.mosquittoclient;

import lombok.RequiredArgsConstructor;


/*DeviceRunner è una classe che rappresenta un oggetto eseguibile in un thread, il cui
comportamento principale è invocare il metodo subscribe() */

@RequiredArgsConstructor
public class DeviceRunner implements Runnable {
	
	final private Device device;
	
	@Override
	public void run() {
		device.subscribe();

	}

}
