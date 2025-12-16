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

ENV POSTGRESQL_HOST=postgresql
ENV POSTGRESQL_PASSWORD=postgres
ENV POSTGRESQL_USER=postgres
ENV POSTGRESQL_DBNAME=trades
ENV POSTGRESQL_PROTOCOL=postgresql
ENV POSTGRESQL_SETUP=none
ENV POSTGRESQL_OPTIONS="/trades?sslmode=disable"
ENV POSTGRESQL_PORT=5432
ENV POSTGRESQL_DIALECT=PostgreSQLDialect

ENV OTEL_SERVICE_NAME=recorder-java
ENV OTEL_RESOURCE_ATTRIBUTES="service.version=1.0"
ENV OTEL_EXPORTER_OTLP_ENDPOINT=http://opentelemetry-kube-stack-daemon-collector.opentelemetry-operator-system.svc.cluster.local:4318

EXPOSE 9003
ENTRYPOINT ["java", \
"-javaagent:edot-javaagent.jar", \
"-Dotel.inferred.spans.enabled=true", "-Dotel.inferred.spans.sampling.interval=1ms", "-Dotel.inferred.spans.min.duration=0ms", "-Dotel.inferred.spans.included.classes=com.example.*", \
"-jar", "/usr/src/app/recorder.jar"]
