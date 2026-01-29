package com.kristian.bffagendadortarefas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.swing.*;

@SpringBootApplication
@EnableFeignClients//Ela diz: “Spring, procure interfaces anotadas com @FeignClient e transforme elas em beans.”
@EnableScheduling//Ela diz: “Spring, procure métodos anotados com @Scheduled e execute eles automaticamente.”
public class BffAgendadorTarefasApplication {

	public static void main(String[] args) {
		SpringApplication.run(BffAgendadorTarefasApplication.class, args);
	}

}
