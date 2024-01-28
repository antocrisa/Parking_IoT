package com.parkinglot.mqtt;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class MqttConfig {

	@Value("${parkinglot.mqtt.hostname}")
	private String mqttHost;

	@Value("${parkinglot.mqtt.port}")
	private int mqttPort;

	@Value("${parkinglot.mqtt.username}")
	private String mqttUsername;

	@Value("${parkinglot.mqtt.password}")
	private String mqttPassword;
	
	@Autowired
	SslUtil sslUtils;

	@Bean
	@ConfigurationProperties(prefix = "parkinglot.mqtt")
	public MqttConnectOptions mqttConnectOptions() {
		MqttConnectOptions options = new MqttConnectOptions();
		options.setUserName(mqttUsername);
		options.setPassword(mqttPassword.toCharArray());
		options.setAutomaticReconnect(true);
		options.setCleanSession(true);
		options.setSocketFactory(sslUtils.getSSLSocketFactory());
		options.setHttpsHostnameVerificationEnabled(false);
		return options;
	}

	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public IMqttClient mqttClient() throws MqttException {
		MemoryPersistence persistence = new MemoryPersistence();
		IMqttClient mqttClient = new MqttClient("ssl://" + mqttHost + ":" + mqttPort, getClientId(), persistence);
		mqttClient.setCallback(new MqttCallback() {

			@Override
			public void messageArrived(String topic, MqttMessage message) throws Exception {
			}

			@Override
			public void deliveryComplete(IMqttDeliveryToken token) {
			}

			@Override
			public void connectionLost(Throwable cause) {
				try {
					mqttClient();
				} catch (MqttException e) {
					System.out.println(e.getMessage());
				}
			}
		});
		mqttClient.connect(mqttConnectOptions());
		return mqttClient;
	}

	private String getClientId() {
		String uuid = UUID.randomUUID().toString();
		return "parkinglot-" + uuid.substring(0, 8);
	}

}