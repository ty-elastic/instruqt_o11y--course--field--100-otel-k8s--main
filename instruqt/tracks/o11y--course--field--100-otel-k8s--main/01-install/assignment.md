---
slug: install
id: pfxljoofuqb2
type: challenge
title: Installing OpenTelemetry in Kubernetes
tabs:
- id: cvuhdep6nf7c
  title: Elasticsearch
  type: service
  hostname: kubernetes-vm
  path: /
  port: 30001
- id: wtymmwmcwnup
  title: Terminal
  type: terminal
  hostname: host-1
  workdir: /workspace/workshop
- id: tcgpla2ouph4
  title: Source
  type: code
  hostname: host-1
  path: /workspace/workshop/src
difficulty: ""
timelimit: 0
lab_config:
  custom_layout: '{"root":{"children":[{"branch":{"size":67,"children":[{"leaf":{"tabs":["cvuhdep6nf7c","tcgpla2ouph4"],"activeTabId":"cvuhdep6nf7c","size":82}},{"leaf":{"tabs":["wtymmwmcwnup"],"activeTabId":"wtymmwmcwnup","size":15}}]}},{"leaf":{"tabs":["assignment"],"activeTabId":"assignment","size":31}}],"orientation":"Horizontal"}}'
enhanced_loading: null
---
We have our application stack running on Kubernetes. Now let's observe it using Elastic!

# Install the OpenTelemetry Operator

With the advent of the OpenTelemetry Operator and related Helm chart, you can now easily deploy an entire observability signal collection package for Kubernetes, inclusive of:
* application traces, metrics, and logs
* infrastructure traces (nginx), metrics and logs
* application and infrastructure metrics

1. [button label="Elastic"](tab-0)
2. Click `Add Data` in bottom-left pane
3. Click `Kubernetes`
4. Click `OpenTelemetry (Full Observability)`
6. Click `Copy to clipboard` below `Add the OpenTelemetry repository to Helm`
7. [button label="Terminal"](tab-1)
8. Paste and run helm chart command
9. [button label="Elastic"](tab-0)
10. Click `Copy to clipboard` below `Install the OpenTelemetry Operator`
11. [button label="Terminal"](tab-1)
12. Paste and run k8s commands to install OTel operator

## Checking the Install

First, list out the available namespaces:
```bash,run
kubectl get namespaces
```
And get a list of pods running in the `opentelemetry-operator-system` namespace:
```bash,run
kubectl -n opentelemetry-operator-system get pods
```

And let's look at the logs from the daemonset collector to see if it is exporting to Elasticsearch without error...

# Checking Observability

Let's confirm what signals are coming into Elastic.

First, let's check for logs. Navigate to the [button label="Elastic"](tab-0) tab and click on `Observability` > `Discover`.

Next, let's check for infrastructure metrics. Navigate to the [button label="Elastic"](tab-0) tab and click on `Infrastructure` > `Hosts`.

Finally, let's check for application traces. Navigate to the [button label="Elastic"](tab-0) tab and click on `Applications` > `Service Inventory`. Note that there is not yet any APM data flowing in.

Let's figure out what's up.
