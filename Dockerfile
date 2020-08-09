FROM adoptopenjdk:11-jre-hotspot as builder
WORKDIR application
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM adoptopenjdk:11-jre-hotspot
# Add the app user and step in the directory
RUN groupadd appusers && useradd -g appusers appuser
USER appuser:appusers
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./

ENTRYPOINT java org.springframework.boot.loader.JarLauncher
