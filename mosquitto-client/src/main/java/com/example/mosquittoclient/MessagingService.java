package com.example.mosquittoclient;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {

	@Autowired
	private IMqttClient mqttClient;
	
	//retained indica al broker se il messaggio deve essere mantenuto anche per i nuovi sottoscrittori
	/*QoS 0: Il messaggio viene consegnato al destinatario al più una volta.
	QoS 1: Il messaggio viene consegnato al destinatario almeno una volta.
	QoS 2: Garantisce che il messaggio viene ricevuto esattamente una volta dal destinatario.*/
	
	public void publish(final String topic, final String payload, int qos, boolean retained)
			throws MqttPersistenceException, MqttException {
		
		System.out.println("Parkinglot publisher called for the topic " + topic);
		
		MqttMessage mqttMessage = new MqttMessage();
		mqttMessage.setPayload(payload.getBytes());
		mqttMessage.setQos(qos);
		mqttMessage.setRetained(retained);
		
		mqttClient.publish(topic, mqttMessage);
	}

}