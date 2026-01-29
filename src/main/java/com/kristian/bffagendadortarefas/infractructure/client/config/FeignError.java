package com.kristian.bffagendadortarefas.infractructure.client.config;

import com.kristian.bffagendadortarefas.infractructure.exceptions.BusinessException;
import com.kristian.bffagendadortarefas.infractructure.exceptions.ConflictException;
import com.kristian.bffagendadortarefas.infractructure.exceptions.ResourceNotFoundException;
import com.kristian.bffagendadortarefas.infractructure.exceptions.UnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignError implements ErrorDecoder {//implements ErrorDecoder = “Sempre que o Feign receber um erro HTTP, passa por mim”
//tratamento + tradução + padronização
    @Override
    public Exception decode(String s, Response response) {
        switch (response.status()){
            case 409:
                return new ConflictException("Erro atributo já existente ");
            case 403:
                return new ResourceNotFoundException("Erro abributo não encontrado");
            case 401:
                return new UnauthorizedException("Erro usuário não autorizado");
            default:
                return new BusinessException("Erro de servidor");
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