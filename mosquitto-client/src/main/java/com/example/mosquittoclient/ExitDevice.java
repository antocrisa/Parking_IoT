package com.example.mosquittoclient;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExitDevice extends Device{
	
	
	@Autowired
	private IMqttClient mqttClient;
	
	@Autowired
	private MessagingService messagingService;
	
	private String exitRequested = "exitRequested";
	private String exitCompleted = "exitCompleted";

	
	public void subscribe() {
		IMqttMessageListener iMessageListener = (topic, msg) -> {
			System.out.println();
			System.out.println("Exit device got new message in the topic " + topic);
			
			String [] tokens = (new String(msg.getPayload())).split(":");
			String user = tokens[0];
			String parkingLotStr = tokens[1].replaceAll("\\D+", ""); // Rimuove tutti i caratteri non numerici
			int parkingLotNo = Integer.parseInt(parkingLotStr);
			
			System.out.println("Waiting for the user " + user +" to exit in the parking lot " + parkingLotNo + "......");
			
			messagingService.publish(topicprefix + exitCompleted, "Parking " + parkingLotNo + " : " + user + " : successfully exited",2,false);
			
			System.out.println( user +" exited successfully");
			System.out.println();
		};
		
		try {
			mqttClient.subscribeWithResponse(topicprefix + exitRequested, iMessageListener);
			System.out.println("Exit Device successfully subscribed in the topic " + topicprefix +  exitRequested);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
	}

}
