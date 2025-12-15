---
slug: auto
id: nktxseynvc2i
type: challenge
title: Automatic Instrumentation
tabs:
- id: ygexxs6w6mfn
  title: Elasticsearch
  type: service
  hostname: kubernetes-vm
  path: /app/discover#/?_g=(filters:!(),query:(language:kuery,query:''),refreshInterval:(pause:!t,value:60000),time:(from:now-1h,to:now))&_a=(breakdownField:log.level,columns:!(),dataSource:(type:esql),filters:!(),hideChart:!f,interval:auto,query:(esql:'FROM%20logs-*'),sort:!(!('@timestamp',desc)))
  port: 30001
- id: 06ew3qjpy0ox
  title: Terminal
  type: terminal
  hostname: host-1
  workdir: /workspace/workshop
- id: xef7bpck9pat
  title: Dockerfile
  type: code
  hostname: host-1
  path: /workspace/workshop/src/recorder-java/Dockerfile
difficulty: ""
timelimit: 0
lab_config:
  custom_layout: '{"root":{"children":[{"branch":{"size":67,"children":[{"leaf":{"tabs":["ygexxs6w6mfn","xef7bpck9pat"],"activeTabId":"ygexxs6w6mfn","size":82}},{"leaf":{"tabs":["06ew3qjpy0ox"],"activeTabId":"06ew3qjpy0ox","size":15}}]}},{"leaf":{"tabs":["assignment"],"activeTabId":"assignment","size":31}}],"orientation":"Horizontal"}}'
enhanced_loading: null
---
# Automatic Instrumentation

Currently, we are injecting the OTel SDK into our applications using the Kubernetes OTel Operator. Let's say you aren't using the Kubernetes OTel Operator, or Kubernetes at all. How do you attach the OTel SDK to your applications?

# Java

Attaching the Java OTel SDK at runtime is similar to other languages supported by OTel.

Navigate to the [button label="Dockerfile"](tab-2).

We've already made a few changes to note:
1. manually download the EDOT Java SDK:
```nocopy
ARG EDOT_VERSION=1.5.0
RUN wget -O edot-javaagent.jar https://repo1.maven.org/maven2/co/elastic/otel/elastic-otel-javaagent/$EDOT_VERSION/elastic-otel-javaagent-$EDOT_VERSION.jar 
```
2. manually inject the SDK at runtime:
```nocopy
ENTRYPOINT ["java", \
"-javaagent:edot-javaagent.jar", \
...
```

Let's verify that we are receiving traces from this service since we switched from using the Kubernetes OTel Operator to manually injecting the OTel SDK:

1. Open the [button label="Elasticsearch"](tab-0) tab
2. Click `Discover` in the left-hand navigation pane
3. Set the time picker to show the last 15 minutes
4. Execute the following query:
```esql
FROM traces-*
| WHERE service.name == "recorder-java"
```

uh-oh; it looks like we stopped receiving traces from the `recorder-java` service when we switched to manual injection. Let's debug and see what's going on.

## Debugging

1. Switch to the [button label="Terminal"](tab-1) tab
2. Let's look at logs from `recorder-java`
3. Get pods
```bash,run
kubectl -n trading-1 get pods
```
4. Look for the instance of the `recorder-java` pod and get the logs:
```bash,run
kubectl -n trading-1 logs recorder-java-XXXX
```
5. Note the errors indicating an inability to export spans

Why is that?

We need to tell the OTel SDK where to send span data.

1. Navigate to the [button label="Dockerfile"](tab-2)
2. Uncomment the line `ENV OTEL_EXPORTER_OTLP_ENDPOINT=http://opentelemetry-kube-stack-daemon-collector.opentelemetry-operator-system.svc.cluster.local:4318`
3. Rebuild:
```bash,run
./build.sh -d force -b true -s recorder-java -l true
```

Wait for the `recorder-java` pod to restart:
```bash,run
kubectl -n trading-1 get pods
```

And once it has restarted, let's describe it again:
```bash,run
kubectl -n trading-1 describe pod recorder-java
```

Note the presence of `OTEL_EXPORTER_OTLP_ENDPOINT`.

Let's check if we are receiving span data:

1. Open the [button label="Elasticsearch"](tab-0) tab
2. Click `Discover` in the left-hand navigation pane
3. Set the time picker to show the last 15 minutes
4. Execute the following query:
```esql
FROM traces-*
| WHERE service.name == "recorder-java"
```

Indeed, we are now receiving span data.
