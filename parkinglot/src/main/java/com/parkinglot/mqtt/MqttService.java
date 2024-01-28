package com.parkinglot.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.parkinglot.exception.NotFoundException;

@Component
public class MqttService {

	@Autowired
	IMqttClient mqttClient;

	public void publish(String topic, String message) {
		try {
			MqttMessage mqttMessage = new MqttMessage();
			mqttMessage.setQos(1);
			mqttMessage.setPayload(message.getBytes());

			System.out.println("-----------------------Publish message--------------------");
			System.out.println("Topic : " + topic);
			System.out.println("Message : " + message);
			System.out.println("----------------------------------------------------------");
			
			mqttClient.publish(topic, mqttMessage);
		} catch (MqttException exp) {
			throw new NotFoundException("Exeption occured in MQTT publish : error - " + exp.getMessage());
		}
	}

}
