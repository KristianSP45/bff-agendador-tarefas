package com.kristian.bffagendadortarefas.infractructure.client.config;

import com.kristian.bffagendadortarefas.infractructure.exceptions.*;
import com.kristian.bffagendadortarefas.infractructure.exceptions.IllegalArgumentException;
import feign.Response;//Response > resposta HTTP real
import feign.codec.ErrorDecoder;//ErrorDecoder > interface que permite tratar erros do Feign

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FeignError implements ErrorDecoder {//implements ErrorDecoder = “Sempre que o Feign receber um erro HTTP, passa por mim”
//tratamento + tradução + padronização
    private static final String ERROR_PREFIX = "Erro: ";

    @Override
    public Exception decode(String s, Response response) {
        //String s > nome do método Feign que falhou (não usado aqui, mas útil pra log)
        //Response response > resposta HTTP completa

        String mensagemErro= mensagemErro(response);

        switch (response.status()){//Decide qual exceção lançar com base no status.
            case 409:
                return new ConflictException(ERROR_PREFIX+mensagemErro);
            case 403:
                return new ResourceNotFoundException(ERROR_PREFIX+mensagemErro);
            case 401:
                return new UnauthorizedException(ERROR_PREFIX+mensagemErro);
            case 400:
                return new IllegalArgumentException(ERROR_PREFIX+mensagemErro);
            default:
                return new BusinessException(ERROR_PREFIX+mensagemErro);
        }
    }

    private String mensagemErro(Response response){
        try {
            if (Objects.isNull(response.body())){
                return "";
            }
            return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
            //response.body() > Isso é o corpo da resposta HTTP.
            //.asInputStream() > Transforma o corpo da resposta em um fluxo de dados. (é como abrir uma torneira de bytes)
            //.readAllBytes() > Lê tudo que veio nessa torneira.
            //StandardCharsets.UTF_8 > diz ao Java COMO interpretar os bytes.
        } catch (IOException e) {
            throw new ErroException(e.getMessage());
        }
    }
}
//Parâmetros:
//String s:
//nome do método Feign que falhou
//ex: TarefasClient#gravarTarefas
//você não usou, mas poderia (logs)

//Response response:
//resposta HTTP real
//status, headers, body