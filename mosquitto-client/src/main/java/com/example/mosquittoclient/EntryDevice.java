package com.example.mosquittoclient;

import org.eclipse.paho.client.mqttv3.IMqttClient;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntryDevice extends Device {
	
	//Iniezione di dipendenza di un client MQTT
	@Autowired
	private IMqttClient mqttClient;
	//Iniezione di dipendenza per il metodo publish
	@Autowired
	private MessagingService messagingService;
	
	private String entryRequested = "entryRequested";
	private String entryCompleted = "entryCompleted";
	
	
	
	public void subscribe() {
		IMqttMessageListener iMessageListener = (topic, msg) -> {
			
			System.out.println();
			System.out.println("Entry device got new message in the topic " + topic);
		
			String [] tokens = (new String(msg.getPayload())).split(":");

			String user = tokens[0];
			String parkingLotStr = tokens[1].replaceAll("\\D+", ""); // Rimuove tutti i caratteri non numerici
			int parkingLotNo = Integer.parseInt(parkingLotStr);

			//Arrays.stream(tokens).forEach(element -> System.out.print(element + " "));
			
			System.out.println("Waiting for the user " + user + " in the parking lot " + parkingLotNo + " to enter......");
			messagingService.publish(topicprefix +  entryCompleted, "Parking " + parkingLotNo + " : " + user + " : successfully entered",1,false);
			System.out.println( user +" entered successfully");
			System.out.println();
		};
		
		//In ascolto sul topic "requested". In caso di msg, viene eseguito il comportamento di iMessageListener pubblicando in "completed"
		try {
			mqttClient.subscribeWithResponse(topicprefix + entryRequested, iMessageListener);
			System.out.println("Entry Device successfully subscribed in the topic " + topicprefix + entryRequested);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
	}
	
	

}
