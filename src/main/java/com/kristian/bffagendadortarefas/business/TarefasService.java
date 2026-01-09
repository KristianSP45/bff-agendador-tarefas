package com.kristian.bffagendadortarefas.business;

import com.kristian.bffagendadortarefas.business.dto.in.TarefasDTORequest;
import com.kristian.bffagendadortarefas.business.dto.out.TarefasDTOResponse;
import com.kristian.bffagendadortarefas.business.enums.StatusNotificacaoEnum;
import com.kristian.bffagendadortarefas.infractructure.client.TarefasClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarefasService {

    private final TarefasClient tarefasClient;

    public TarefasDTOResponse gravarTarefa(String token, TarefasDTORequest dto){
        return tarefasClient.gravarTarefas(dto, token);
    }

    public List<TarefasDTOResponse> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal, String token){
        return tarefasClient.buscaListaTarefasPorPeriodo(dataInicial, dataFinal, token);
    }

    public List<TarefasDTOResponse> buscaTarefasPorEmail(String token){
        return tarefasClient.buscaTarefasPorEmail(token);
    }

    public void deletarTarefaPorId(String id, String token){
        tarefasClient.deletarTarefaPorId(id, token);
    }

    public TarefasDTOResponse alterarStatus(StatusNotificacaoEnum status, String id, String token){
        return tarefasClient.alterarStatusNotificacao(status, id, token);
    }

    public TarefasDTOResponse updateTarefas(TarefasDTORequest dto, String id, String token){
        return tarefasClient.updateTarefas(dto, id, token);
    }
}