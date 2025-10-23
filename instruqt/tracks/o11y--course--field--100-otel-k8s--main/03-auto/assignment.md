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
  path: /
  port: 30001
- id: 06ew3qjpy0ox
  title: Terminal
  type: terminal
  hostname: host-1
  workdir: /workspace/workshop
- id: xef7bpck9pat
  title: Source
  type: code
  hostname: host-1
  path: /workspace/workshop/
difficulty: ""
timelimit: 0
lab_config:
  custom_layout: '{"root":{"children":[{"branch":{"size":67,"children":[{"leaf":{"tabs":["ygexxs6w6mfn","xef7bpck9pat"],"activeTabId":"ygexxs6w6mfn","size":82}},{"leaf":{"tabs":["06ew3qjpy0ox"],"activeTabId":"06ew3qjpy0ox","size":15}}]}},{"leaf":{"tabs":["assignment"],"activeTabId":"assignment","size":31}}],"orientation":"Horizontal"}}'
enhanced_loading: null
---
# Automatic Instrumentation

Let's say you aren't using the Kubernetes OTel Operator, or Kubernetes at all. How do you attach the OTel SDK to your applications?

## Java Attach

Navigate to the [button label="Source"](tab-2) tab and open `src/recorder-java/Dockerfile`.

## OTel Variables

How did the agent know where to send spans? We have instructed the Kubernetes OTel Operator inject these variables automatically. We could have also defined them manually.

Navigate to the [button label="Source"](tab-2) tab and open `k8s/yaml/recorder-java.yaml`.