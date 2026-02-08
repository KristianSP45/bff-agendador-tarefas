#FROM eclipse-temurin:21-jdk
#   “Quero um Linux já com Java 21 instalado.”
#WORKDIR /app
#   Define o diretório de trabalho dentro do container
#COPY target/bff-agendador-tarefas-0.0.1-SNAPSHOT.jar /app/bff-agendador-tarefas.jar
#   Copia o JAR gerado pelo Maven do seu PC > pra dentro do container
#   Lado esquerdo: Arquivo que o Maven gerou, está no host
#   Lado direito: Caminho dentro do container
#EXPOSE 8083
#   Documenta que a aplicação escuta na porta 8083
#CMD ["java", "-jar", "/app/bff-agendador-tarefas.jar"]
#   Define o comando principal do container

#linha de divisão entre um dockerfile simples e avançado

FROM maven:3.8-eclipse-temurin-21 AS BUILD
#   Cria uma imagem temporária que tem: Linux, Java 21, Maven
WORKDIR /app
COPY . .
#Copia todo o projeto pra dentro do container: código, pom.xml, src/

RUN mvn clean install -DskipTest
#Executa dentro do container: baixa dependências, compila, gera o JAR em /app/target
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
#   --from=build : “não copia do meu PC copia de outro estágio do Dockerfile”
#   *.jar é só: “qualquer JAR que estiver aí”
#   app.jar: “pega o JAR do container build e salva aqui como app.jar”
EXPOSE 8083
CMD ["java", "-jar", "/app/bff-agendador-tarefas.jar"]