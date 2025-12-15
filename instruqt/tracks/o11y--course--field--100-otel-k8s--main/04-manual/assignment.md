---
slug: manual
id: j6ssy89xmfq7
type: challenge
title: Adding Manual Instrumentation
tabs:
- id: zktw3svujerw
  title: Elasticsearch
  type: service
  hostname: kubernetes-vm
  path: /
  port: 30001
- id: 8vdntb5bahmc
  title: Terminal
  type: terminal
  hostname: host-1
  workdir: /workspace/workshop
- id: tw3f0m3x2ztt
  title: Source
  type: code
  hostname: host-1
  path: /workspace/workshop
difficulty: ""
timelimit: 0
lab_config:
  custom_layout: '{"root":{"children":[{"branch":{"size":67,"children":[{"leaf":{"tabs":["zktw3svujerw","tw3f0m3x2ztt"],"activeTabId":"zktw3svujerw","size":82}},{"leaf":{"tabs":["8vdntb5bahmc"],"activeTabId":"8vdntb5bahmc","size":15}}]}},{"leaf":{"tabs":["assignment"],"activeTabId":"assignment","size":31}}],"orientation":"Horizontal"}}'
enhanced_loading: null
---
# Setting Up Manual Instrumentation

## Adding Dependencies

1. Navigate to the [button label="Source"](tab-2) tab and open `src/recorder-java/pom.xml`.

Note that we are explicitly linking the OTel SDK to our code at build-time rather than run-time.

## Instantiating

1. Navigate to the [button label="Source"](tab-2) tab and open `src/recorder-java/src/main/java/com/example/recorder/Main.java`.

Note that we are initializing a global tracer object we can later use to manually create spans.

# Adding instrumentation

## Annotations

For languages that support annotations (e.g., Python and Java), the OTel SDK lets you instrument with annotations.

Modify `src/recorder-java/src/main/java/com/example/recorder/TradeService.java` like

```java
    @WithSpan
    public void auditCustomer(@SpanAttribute(Main.ATTRIBUTE_PREFIX + "customerId") String customerId) {
        log.info("trading for " + customerId);
    }
```

## Manual Span Creation

Modify `src/recorder-java/src/main/java/com/example/recorder/TradeService.java` like

```java
    public void auditSymbol(String symbol) {
        Span span = tracer.spanBuilder("auditSymbol").startSpan();
        try (Scope ignored = span.makeCurrent()) {
            span.setAttribute(Main.ATTRIBUTE_PREFIX + "symbol", symbol);
             log.info("trading symbol" + symbol);
        } finally {
            span.end();
        }
    }
```

## Rebuilding

Rebuild and deploy the `recorder-java` service:
```bash,run
./build.sh -d force -b true -s recorder-java -l true
```

And let's recheck our `recorder-java` service. 

1. Navigate to the [button label="Elastic"](tab-0) tab and click on `Applications` > `Service Inventory`
2. Click on the `recorder-java` service
3. Select the `POST /record` transaction
4. Click on the `auditSymbol` span
5. Note that `auditSymbol` has `symbol` as a span attribute
6. Close the flyout
7. Click on the `auditCustomer` span
8. Note that `auditCustomer` has `customerId` as a span attribute
