package com.kristian.bffagendadortarefas.controller;

import com.kristian.bffagendadortarefas.business.dto.in.TarefasDTORequest;
import com.kristian.bffagendadortarefas.infractructure.security.SecurityConfig;
import com.kristian.bffagendadortarefas.business.TarefasService;
import com.kristian.bffagendadortarefas.business.dto.out.TarefasDTOResponse;
import com.kristian.bffagendadortarefas.business.enums.StatusNotificacaoEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tarefas")
@Tag(name = "Tarefas", description = "Cadastra tarefas de usuários")// Agrupa os endpoints de tarefas no Swagger, modo de organização
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)//“Este controller usa o esquema de segurança definido em SecurityConfig”
// Indica ao Swagger que este controller exige autenticação (documentação apenas)
public class TarefasController {
    private final TarefasService tarefasService;

    @PostMapping
    @Operation(summary = "Salvar Tarefas de Usuário", description = "Cria um nova tarefa")//Documentação = summary > título curto / description > explicação detalhada
    @ApiResponse(responseCode = "200", description = "Tarefa salvo com sucesso")//Documentação = ApiResponse > possíveis retornos HTTP
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TarefasDTOResponse> gravarTarefas(@RequestBody TarefasDTORequest dto,
                                                            @RequestHeader(value = "Authorization", required = false) String token){
        // Header Authorization opcional para permitir validação manual no service
        // required = true (padrão): Spring bloqueia antes do método
        // required = false: Método é executado, token pode ser null e Service decide o que fazer
        return  ResponseEntity.ok(tarefasService.gravarTarefa(token, dto));
    }

    @GetMapping("/eventos")
    @Operation(summary = "Busca tarefas por Período",
            description = "Busca tarefas cadastradas por período")
    @ApiResponse(responseCode = "200", description = "Tarefas encontradas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<List<TarefasDTOResponse>> buscaListaTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,// Define o formato ISO para conversão de String para LocalDateTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal,
            @RequestHeader(value = "Authorization", required = false) String token){
        return  ResponseEntity.ok(tarefasService.buscaTarefasAgendadasPorPeriodo(dataInicial, dataFinal, token));
    }

    @GetMapping
    @Operation(summary = "Busca tarefas por Email de Usuário",
            description = "Busca tarefas cadastradas por email de usuário")
    @ApiResponse(responseCode = "200", description = "Tarefas encontradas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @ApiResponse(responseCode = "403", description = "Email não encontrado")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<List<TarefasDTOResponse>> buscaTarefasPorEmail(@RequestHeader(value = "Authorization", required = false) String token){
        return ResponseEntity.ok(tarefasService.buscaTarefasPorEmail(token));
    }

    @DeleteMapping
    @Operation(summary = "Deleta Tarefas por Id",
            description = "Deleta tarefas por Id")
    @ApiResponse(responseCode = "200", description = "Tarefas encontradas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @ApiResponse(responseCode = "403", description = "Tarefa id não encontrado")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<Void> deletarTarefaPorId(@RequestParam("id") String id,
                                                   @RequestHeader(value = "Authorization", required = false) String token){
        tarefasService.deletarTarefaPorId(id, token);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    @Operation(summary = "Altera Status de Tarefas",
            description = "Altera status de tarefas")
    @ApiResponse(responseCode = "200", description = "Status de tarefa alterada")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @ApiResponse(responseCode = "403", description = "Tarefa id não encontrado")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public ResponseEntity<TarefasDTOResponse> alterarStatusNotificacao(@RequestParam("status") StatusNotificacaoEnum status,
                                                                       @RequestParam("id") String id,
                                                                       @RequestHeader(value = "Authorization", required = false) String token){
        return ResponseEntity.ok(tarefasService.alterarStatus(status, id, token));
    }

    @PutMapping
    @Operation(summary = "Altera Dados Tarefas",
            description = "Altera dados tarefas")
    @ApiResponse(responseCode = "200", description = "Tarefas alteradas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    @ApiResponse(responseCode = "403", description = "Tarefa id não encontrado")
    @ApiResponse(responseCode = "401", description = "Usuário não autorizado")
    public  ResponseEntity<TarefasDTOResponse> updateTarefas(@RequestBody TarefasDTORequest dto, @RequestParam("id") String id,
                                                             @RequestHeader(value = "Authorization", required = false) String token){
        return  ResponseEntity.ok(tarefasService.updateTarefas(dto, id, token));
    }
}
