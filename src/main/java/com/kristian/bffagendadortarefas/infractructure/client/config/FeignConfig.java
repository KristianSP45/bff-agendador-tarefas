package com.kristian.bffagendadortarefas.infractructure.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;

@Configuration//“Essa classe define beans de configuração da aplicação”
public class FeignConfig {

    @Bean
    public FeignError feignError(){
    return new FeignError();
    }
    // FeignError traduz erros HTTP das APIs externas(client) em exceções do BFF
    // FeignConfig registra esse tradutor para que o Feign o utilize automaticamente
}
