# bff-agendador-tarefas

BFF (**Backend For Frontend**) responsável por centralizar e orquestrar as operações do ecossistema **Agendador de Tarefas**, integrando os microsserviços de **Usuário**, **Tarefas** e **Notificação** através do **OpenFeign**.

Além de expor uma interface unificada para o frontend, o serviço executa um **agendamento automático** que busca tarefas próximas do horário do evento e dispara notificações por e-mail.

## Tecnologias

* Java 21
* Spring Boot 3.5.9
* Spring Web
* OpenFeign
* Swagger/OpenAPI
* Lombok
* Maven
* Apache HttpClient 5

## Melhorias implementadas

* Orquestração centralizada dos microsserviços
* Integração com **Usuário**, **Tarefas** e **Notificação** via OpenFeign
* Tratamento global de erros de APIs externas com **FeignError**
* Agendamento automático com **@Scheduled**
* Configuração de **CORS** para integração com frontend Angular
* Documentação completa com **Swagger/OpenAPI** e suporte a **Bearer JWT**

## Endpoints

### Usuários

| Método | Endpoint                    | Descrição                  |
| ------ | --------------------------- | -------------------------- |
| POST   | `/usuario`                  | Cadastrar usuário          |
| POST   | `/usuario/login`            | Autenticar usuário         |
| GET    | `/usuario?email={email}`    | Buscar usuário por e-mail  |
| PUT    | `/usuario`                  | Atualizar dados do usuário |
| DELETE | `/usuario/{email}`          | Remover usuário            |
| POST   | `/usuario/endereco`         | Cadastrar endereço         |
| PUT    | `/usuario/endereco?id={id}` | Atualizar endereço         |
| POST   | `/usuario/telefone`         | Cadastrar telefone         |
| PUT    | `/usuario/telefone?id={id}` | Atualizar telefone         |
| GET    | `/usuario/endereco/{cep}`   | Consultar CEP via ViaCEP   |

### Tarefas

| Método | Endpoint                           | Descrição                             |
| ------ | ---------------------------------- | ------------------------------------- |
| POST   | `/tarefas`                         | Cadastrar tarefa                      |
| GET    | `/tarefas`                         | Listar tarefas do usuário autenticado |
| GET    | `/tarefas/eventos`                 | Buscar tarefas pendentes por período  |
| PUT    | `/tarefas?id={id}`                 | Atualizar tarefa                      |
| PATCH  | `/tarefas?id={id}&status={status}` | Alterar status da notificação         |
| DELETE | `/tarefas?id={id}`                 | Remover tarefa                        |

### Exemplo de login

```json
{
  "email": "usuario@email.com",
  "senha": "123456"
}
```

## Segurança

A API utiliza **Bearer JWT** documentado no Swagger através do `@SecurityScheme`.

### Header obrigatório para rotas protegidas

```http
Authorization: Bearer <token>
```

O token recebido pelo BFF é repassado automaticamente para os microsserviços integrados.

## Microsserviços integrados

O BFF se comunica com os seguintes serviços:

* **usuario** → gerenciamento e autenticação de usuários
* **Agendador-tarefas** → gerenciamento de tarefas e eventos
* **notificacao** → envio de notificações por e-mail

## Agendamento automático

O serviço possui um **CronService** que executa automaticamente:

1. Login técnico para obtenção de token JWT
2. Busca de tarefas agendadas para a **próxima hora**
3. Envio de notificações por e-mail
4. Atualização do status da tarefa para **NOTIFICADO**

Esse comportamento é controlado pela propriedade:

```properties
cron.horario=0 0/5 * * * ?
```

## Configuração de CORS

O projeto possui configuração de **CORS** liberando acesso para aplicações frontend executadas em:

```
http://localhost:4200
```

## Tratamento de exceções

* `ResourceNotFoundException` → 404
* `ConflictException` → 409
* `IllegalArgumentException` → 400
* `UnauthorizedException` → 401
* `BusinessException` → regras de negócio e integração
* `ErroException` → falhas internas de integração
* `GlobalExceptionHandler` → padronização das respostas de erro

## Testes

No momento, esta API **não possui testes unitários implementados**.

## Automação / CI

O projeto possui GitHub Actions configurado para automação de build e integração contínua, incluindo validações executadas automaticamente em pushes e pull requests.

## Como executar

```bash
git clone https://github.com/KristianSP45/bff-agendador-tarefas
cd bff-agendador-tarefas
mvn spring-boot:run
```

A aplicação ficará disponível em:

```
http://localhost:8083
```

## Swagger

Após iniciar a aplicação, acesse:

```
http://localhost:8083/swagger-ui/index.html
```

## Docker

O projeto possui suporte para **Docker** e **Docker Compose**.

### Comandos principais

```bash
docker-compose up -d
docker-compose down
```

## Observações

* Projeto desenvolvido durante um **curso prático de Spring Boot e Microsserviços**, acompanhando as aulas e realizando implementações junto à instrutora.
* O serviço atua como **camada de orquestração** entre frontend e microsserviços, reduzindo o acoplamento do cliente com os serviços internos.
* Posteriormente foram adicionadas melhorias como **Swagger com JWT**, **tratamento customizado de erros do Feign**, **agendamento automático de notificações** e **configuração de CORS para frontend Angular**.

## Autor

**Kristian Pessoa**
