package com.example.mosquittoclient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

//implements CommandLineRunner per eseguire codice aggiuntivo al momento dell'avvio dell'applicazione
//chiama il metodo run automaticamente
//All'avvio, crea e avvia tre dispositivi (EntryDevice, CashDevice, ExitDevice) in thread separati

@SpringBootApplication
@EnableConfigurationProperties
//@RequiredArgsConstructor
public class MosquittoClientApplication   implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MosquittoClientApplication.class, args);
	}
	
	
//	@Autowired
//	private MessagingService messagingService; 
	@Autowired
	private EntryDevice entryDevice;
	@Autowired
	private	CashDevice cashDevice;
	@Autowired
	private ExitDevice exitDevice;
	@Override
	
	public void run(String... args) throws Exception {
		
		System.out.println();
		System.out.println("Welcome to Parking lot");
		System.out.println();
		
		
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		
			Runnable runnable = new DeviceRunner(entryDevice);
			Runnable runnable2 = new DeviceRunner(cashDevice);
			Runnable runnable3 = new DeviceRunner(exitDevice);
			executorService.execute(runnable);
			executorService.execute(runnable2);
			executorService.execute(runnable3);
			
		
	}

}

