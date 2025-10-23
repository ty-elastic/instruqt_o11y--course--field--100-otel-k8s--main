package com.example.recorder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.opentelemetry.instrumentation.annotations.SpanAttribute;

import io.opentelemetry.context.Scope;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;

/**
 * Service layer is where all the business logic lies
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TradeService {
	private final TradeNotifier tradeNotifier;
	private final TradeRecorder tradeRecorder;

    private final Tracer tracer;

    @WithSpan
    public void auditCustomer(@SpanAttribute(Main.ATTRIBUTE_PREFIX + "trade") String customerId) {
        log.info("trading for " + customerId);
    }

    public void auditSymbol(String symbol) {
        log.info("trading symbol" + symbol);
    }

    @Async
    public CompletableFuture<Trade> processTrade (Trade trade){
        this.auditCustomer(trade.customerId);
        this.auditSymbol(trade.symbol);

        tradeNotifier.notify(trade);

        Trade resp = tradeRecorder.recordTrade(trade);
        return CompletableFuture.completedFuture(resp);
    }
}
