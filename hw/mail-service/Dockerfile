FROM openjdk:17.0.2-jdk-slim-buster as builder

COPY target/*.jar /temp/app.jar
RUN java -Djarmode=layertools -jar /temp/app.jar extract --destination /extract/

FROM openjdk:17.0.2-jdk-slim-buster

ARG APP_HOME=/app
WORKDIR $APP_HOME

COPY --from=builder extract/dependencies/ $APP_HOME/
COPY --from=builder extract/spring-boot-loader/ $APP_HOME/
COPY --from=builder extract/snapshot-dependencies/ $APP_HOME/
COPY --from=builder extract/application/ $APP_HOME/

ENTRYPOINT java $JAVA_OPTS org.springframework.boot.loader.JarLauncher $JAVA_ARGS