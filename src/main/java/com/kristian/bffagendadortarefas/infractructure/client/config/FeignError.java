package com.kristian.bffagendadortarefas.infractructure.client.config;

import com.kristian.bffagendadortarefas.infractructure.exceptions.BusinessException;
import com.kristian.bffagendadortarefas.infractructure.exceptions.ConflictException;
import com.kristian.bffagendadortarefas.infractructure.exceptions.ResourceNotFoundException;
import com.kristian.bffagendadortarefas.infractructure.exceptions.UnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignError implements ErrorDecoder {

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
