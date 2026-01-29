package com.kristian.bffagendadortarefas.business;

import com.kristian.bffagendadortarefas.business.dto.out.TarefasDTOResponse;
import com.kristian.bffagendadortarefas.infractructure.client.EmailClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailClient emailClient;

    public void enviaEmail(TarefasDTOResponse dto){
        emailClient.enviaEmail(dto);
    }
}
