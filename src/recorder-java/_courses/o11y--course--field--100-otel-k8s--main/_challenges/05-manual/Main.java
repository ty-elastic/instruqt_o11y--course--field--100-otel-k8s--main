package com.example.recorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;

@SpringBootApplication
@EnableAsync
public class Main {
	public final static String ATTRIBUTE_PREFIX = "com.example.";

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	public Tracer tracer() {
		OpenTelemetry otel = GlobalOpenTelemetry.get();
		return otel.getTracer(ATTRIBUTE_PREFIX);
	}
}