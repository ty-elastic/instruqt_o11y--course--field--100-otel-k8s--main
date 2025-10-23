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
# Manual Instrumentation

Navigate to the [button label="Source"](tab-2) tab and open `src/recorder-java/pom.xml`.

Navigate to the [button label="Source"](tab-2) tab and open `src/recorder-java/src/main/java/com/example/recorder/Main.java`.
Navigate to the [button label="Source"](tab-2) tab and open `src/recorder-java/src/main/java/com/example/recorder/TradeService.java`.
