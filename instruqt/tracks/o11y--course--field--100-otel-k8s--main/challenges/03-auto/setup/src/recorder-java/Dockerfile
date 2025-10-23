FROM maven:3-eclipse-temurin-17 AS build  
COPY src /usr/src/app/src 
COPY pom.xml /usr/src/app

WORKDIR /usr/src/app
RUN --mount=type=cache,target=/root/.m2 mvn package -Dmaven.test.skip

FROM eclipse-temurin:17

WORKDIR /usr/src/app

ARG EDOT_VERSION=1.5.0
RUN wget -O edot-javaagent.jar https://repo1.maven.org/maven2/co/elastic/otel/elastic-otel-javaagent/$EDOT_VERSION/elastic-otel-javaagent-$EDOT_VERSION.jar 

COPY --from=build /usr/src/app/target/recorder-0.0.1-SNAPSHOT.jar /usr/src/app/recorder.jar

ENV OTEL_SERVICE_NAME="recorder-java"

EXPOSE 9003
ENTRYPOINT ["java", \
"-javaagent:edot-javaagent.jar", \
"-jar", "/usr/src/app/recorder.jar"]
