---
slug: auto
type: challenge
title: Automatic Instrumentation
tabs:
- id: l84sgyu76cnf
  title: Elasticsearch
  type: service
  hostname: kubernetes-vm
  path: /
  port: 30001
- id: jxhyli2lawvz
  title: Terminal
  type: terminal
  hostname: host-1
  workdir: /workspace/workshop
- id: a7wfcgjqgvta
  title: Source
  type: code
  hostname: host-1
  path: /workspace/workshop/
difficulty: ""
timelimit: 0
lab_config:
  custom_layout: '{"root":{"children":[{"branch":{"size":67,"children":[{"leaf":{"tabs":["cvuhdep6nf7c","tcgpla2ouph4"],"activeTabId":"cvuhdep6nf7c","size":82}},{"leaf":{"tabs":["wtymmwmcwnup"],"activeTabId":"wtymmwmcwnup","size":15}}]}},{"leaf":{"tabs":["assignment"],"activeTabId":"assignment","size":31}}],"orientation":"Horizontal"}}'
enhanced_loading: null
---
# Automatic Instrumentation

Let's say you aren't using the Kubernetes OTel Operator, or Kubernetes at all. How do you attach the OTel SDK to your applications?

## Java Attach

Navigate to the [button label="Source"](tab-2) tab and open `src/recorder-java/Dockerfile`.

## OTel Variables

How did the agent know where to send spans? We have instructed the Kubernetes OTel Operator inject these variables automatically. We could have also defined them manually.

Navigate to the [button label="Source"](tab-2) tab and open `k8s/yaml/recorder-java.yaml`.