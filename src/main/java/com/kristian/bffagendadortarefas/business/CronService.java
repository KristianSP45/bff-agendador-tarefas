//CronService é responsável por executar processos automáticos do sistema,
// autenticando-se como usuário técnico, orquestrando serviços e garantindo
// a execução de regras baseadas em tempo, sem depender de requisições externas.
package com.kristian.bffagendadortarefas.business;

import com.kristian.bffagendadortarefas.business.dto.in.LoginRequestDTO;
import com.kristian.bffagendadortarefas.business.dto.out.TarefasDTOResponse;
import com.kristian.bffagendadortarefas.business.enums.StatusNotificacaoEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j//é tipo um out.println(), mas mais profissional e usando com log.info("");
//private static final Logger log = LoggerFactory.getLogger(CronService.class);
public class CronService {

    private final TarefasService tarefasService;
    private final EmailService emailService;
    private final UsuarioService usuarioService;

    @Value("${usuario.email}")
    private String email;
    //Se esse usuário não existir no banco > cron quebra
    @Value("${usuario.senha}")
    private String senha;

    @Scheduled(cron = "${cron.horario}")
    // @Scheduled faz o método ser executado automaticamente pelo Spring.
    // O horário de execução é definido pela expressão cron no application.yml.
    // Um depende do outro: sem @Scheduled o método não roda, sem cron o agendamento não existe.
    public void  buscaTarefasProximaHora(){
        String token = login(converterParaRequestDTO());
    log.info("Iniciada a busca de tarefas");
        LocalDateTime horaAtual = LocalDateTime.now();
        LocalDateTime horaFutura = LocalDateTime.now().plusHours(1);
        //Qualquer tarefa que fique entre hora atual e a hora futura(+1)
        //Se agora é 22h - qualquer tarefa entre 22h e 23h
        //Se agora é 22h - qualquer tarefa entre 23h e 23h05 -- antes

        List<TarefasDTOResponse> listaTarefas =
                tarefasService.buscaTarefasAgendadasPorPeriodo(horaAtual, horaFutura, token);
    log.info("Tarefas encontradas "+listaTarefas);
        listaTarefas.forEach(tarefa -> {
            emailService.enviaEmail(tarefa);
    log.info("Email enviado para o usuário "+tarefa.getEmailUsuario());
            tarefasService.alterarStatus(StatusNotificacaoEnum.NOTIFICADO, tarefa.getId(), token);
        });
    log.info("Finalizada a busca e notificada de tarefas");
    }

    public  String login(LoginRequestDTO dto){
        return usuarioService.loginUsuario(dto);
    }
    // Realiza o login usando uma credencial técnica
    // utilizada pelo processo automático (cron) para obter o token JWT.

    public LoginRequestDTO converterParaRequestDTO(){
        return LoginRequestDTO.builder()
                .email(email)
                .senha(senha)
                .build();
        // Constrói o LoginRequestDTO a partir das credenciais técnicas
        // definidas no application.yml.
    }
}
// @Value("${usuario.email}") e @Value("${usuario.senha}")
// Aqui não é usuário final.
// É um usuário técnico do sistema, tipo: cron@system.com
// Esse usuário existe só para o job rodar.