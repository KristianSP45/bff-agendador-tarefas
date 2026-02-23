package com.kristian.bffagendadortarefas.infractructure.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    //  “Essa classe diz pro Spring:
    //  Meu front (Angular no localhost:4200) pode acessar qualquer rota da API,
    //  usando esses métodos HTTP, com qualquer header, podendo enviar token/cookie.”

    @Bean//fala pro Spring: “isso aqui é um objeto que eu quero que você gerencie”
    public WebMvcConfigurer configCors(){
        //WebMvcConfigurer > interface que permite customizar o comportamento do Spring MVC
        return new WebMvcConfigurer() {//Aqui você cria uma implementação anônima da interface
            //É tipo dizer: “Spring, usa essa implementação aqui”
            @Override
            public void addCorsMappings(CorsRegistry registry) {//CorsRegistry → classe usada pra configurar CORS
                registry.addMapping("/**")//Caminhos que terão CORS liberado > todas as rotas da API
                        .allowedOrigins("http://localhost:4200")//Quem pode acessar > Só permite requisições vindas desse endereço
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")//Métodos HTTP permitidos
                        .allowedHeaders("*")//Headers permitidos > Permite qualquer header
                        .allowCredentials(true)//Permite: cookies, headers de autenticação, token JWT
                        //Se isso for true, não pode usar allowedOrigins("*").
                        .maxAge(360);//Tempo (em segundos) que o navegador memoriza essa configuração > 360 = 6 minutos

            }
        };
    }
}
