---
slug: annotations
id: otyhxlvur3aq
type: challenge
title: Adding Annotations
tabs:
- id: wmoqr5y7enzp
  title: Elasticsearch
  type: service
  hostname: kubernetes-vm
  path: /
  port: 30001
- id: hgw4lwqrrtje
  title: Terminal
  type: terminal
  hostname: host-1
  workdir: /workspace/workshop
- id: mtl1tyjzccvr
  title: Source
  type: code
  hostname: host-1
  path: /workspace/workshop
difficulty: ""
timelimit: 0
lab_config:
  custom_layout: '{"root":{"children":[{"branch":{"size":67,"children":[{"leaf":{"tabs":["wmoqr5y7enzp","mtl1tyjzccvr"],"activeTabId":"wmoqr5y7enzp","size":82}},{"leaf":{"tabs":["hgw4lwqrrtje"],"activeTabId":"hgw4lwqrrtje","size":15}}]}},{"leaf":{"tabs":["assignment"],"activeTabId":"assignment","size":31}}],"orientation":"Horizontal"}}'
enhanced_loading: null
---
# Instrumentation through Annotations

For languages that support annotations (e.g., Python and Java), the OTel SDK lets you instrument with annotations.

Navigate to the [button label="Source"](tab-2) tab and open `src/recorder-java/pom.xml`.

Navigate to the [button label="Source"](tab-2) tab and open `src/recorder-java/src/main/java/com/example/recorder/TradeService.java`.
