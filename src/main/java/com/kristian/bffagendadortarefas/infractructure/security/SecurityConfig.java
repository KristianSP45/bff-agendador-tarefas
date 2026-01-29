package com.kristian.bffagendadortarefas.infractructure.security;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SecurityScheme(name = SecurityConfig.SECURITY_SCHEME, //Define o nome do esquema de segurança no Swagger.
        type = SecuritySchemeType.HTTP,//Diz ao Swagger: “A autenticação é via header HTTP”
        bearerFormat = "JWT",//dica visual/documental: Serve pra quem usa o Swagger entender: “ah, isso aqui é JWT”
        scheme = "bearer")//Diz: “O token vem como Bearer Token”
public class SecurityConfig {
    //Essa classe existe apenas para o Swagger/OpenAPI saber que a API usa Bearer JWT e, assim, mostrar o botão de autenticação.

    public static final String SECURITY_SCHEME = "bearerAuth";//Você está dizendo ao Swagger: “Esse endpoint exige autenticação bearerAuth”
}
//name = SecurityConfig.SECURITY_SCHEME == Depois você usa esse nome aqui
//@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
//Ou seja: é só uma referência de documentação
