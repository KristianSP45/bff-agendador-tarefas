package com.kristian.bffagendadortarefas.infractructure.client;

import com.kristian.bffagendadortarefas.business.dto.out.TarefasDTOResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "notificacao", url = "${notificacao.url}")
public interface EmailClient {

    @PostMapping
    void enviaEmail(@RequestBody TarefasDTOResponse dto);
}
