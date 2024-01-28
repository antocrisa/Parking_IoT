package com.example.mosquittoclient;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CashDevice extends Device {

	
	@Autowired
	private IMqttClient mqttClient;
	@Autowired
	private MessagingService messagingService;
	
	private String paymentRequested = "paymentRequested";
	private String paymentDone = "paymentCompleted";
	
	

	public void subscribe() {
		IMqttMessageListener iMessageListener = (topic, msg) -> {
			
			System.out.println();
			System.out.println("Cash device got new message in the topic " + topic);

			String [] tokens = (new String(msg.getPayload())).split(":");
			String user = tokens[0];
			String parkingLotStr = tokens[1].replaceAll("\\D+", ""); // Rimuove tutti i caratteri non numerici
			int parkingLotNo = Integer.parseInt(parkingLotStr);
			
			System.out.println("Waiting for the user " + user + " in the parking lot " + parkingLotNo + " to pay......");
			messagingService.publish(topicprefix + paymentDone,"Parking " + parkingLotNo + " : " + user + " : successfully paid",1,false);
			System.out.println( user + " made the payment successfully");
			System.out.println();
	
		};
		

		try {
			mqttClient.subscribeWithResponse(topicprefix + paymentRequested, iMessageListener);
			System.out.println("Cash Device successfully subscribed in the topic " + topicprefix + paymentRequested);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
	}
	
}
