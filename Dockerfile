# Usando versão 10.1.0.Final do jboss/wildfly
FROM jboss/wildfly:10.1.0.Final

# Créditos ao criador da imagem
LABEL br.com.multitela.quiz.fabricadesoftware.version="0.0.1-snapshot"
LABEL author="Matheus de S. Siqueira"
LABEL email="matheussiqueira.ec@gmail.com"
LABEL vendor1="Fabrica de Sotware - UFPA"
LABEL br.com.multitela.quiz.fabricadesoftware.realease-date="2018-09-29"
 
# Variáveis de ambiente do PostgreSQL
ENV DB_HOST postgres
ENV DB_PORT 5432
ENV DB_NAME quizapp_fds
ENV DB_USER fabricadesoftware
ENV DB_PASS fabricadesoftware
 
# Definindo nome do DataSource
ENV DS_NAME QuizAppFDS
# Definindo JNDI Name
ENV JNDI_NAME java:/multitela_quiz
 
# Usuário
USER root
 
# Adicionando driver de conexão com PostgreSQL
ADD https://jdbc.postgresql.org/download/postgresql-42.2.4.jar /tmp/postgresql-42.2.4.jar
 
# Definindo diretório de trabalho
WORKDIR /tmp

# Copia arquivos de configuração projeto para a máquina virtual
COPY config_files/wildfly-command.sh ./
COPY config_files/module-install.cli ./

# Roda comandos de instalção e configuração
RUN sed -i -e 's/\r$//' ./wildfly-command.sh
RUN chmod +x ./wildfly-command.sh
RUN ./wildfly-command.sh \
    &&  rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history/
 
# Move a aplicação compilada para o diretório de deployments do JBoss/WildFly
COPY compilado/quiz-app.war $JBOSS_HOME/standalone/deployments
 
# Cria usuário admin do WildFly
RUN $JBOSS_HOME/bin/add-user.sh fabricadesoftware fabricadesoftware --silent
 
# Seta o comando que vai rodar no startup da máquina virtual
# Inicia WidFly no modo standalone e o bota pra escutar em todas as interfaces
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
