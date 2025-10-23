---
slug: debug
id: gwttcdvbryte
type: challenge
title: Debugging OpenTelemetry in Kubernetes
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
  path: /workspace/workshop/k8s/yaml
difficulty: ""
timelimit: 0
lab_config:
  custom_layout: '{"root":{"children":[{"branch":{"size":67,"children":[{"leaf":{"tabs":["cvuhdep6nf7c","tcgpla2ouph4"],"activeTabId":"cvuhdep6nf7c","size":82}},{"leaf":{"tabs":["wtymmwmcwnup"],"activeTabId":"wtymmwmcwnup","size":15}}]}},{"leaf":{"tabs":["assignment"],"activeTabId":"assignment","size":31}}],"orientation":"Horizontal"}}'
enhanced_loading: null
---
# Debugging APM

Let's describe one of our application pods... Specifically, the monkey pod:
```bash,run
kubectl -n trading-1 describe pod monkey
```
Look at the environment variables section; note that there are not yet any OTel environment variables loaded into the pod.

It turns out that after you apply the OTel Operator to your Kubernetes cluster, you need to restart all of your application services:
```bash,run
kubectl -n trading-1 rollout restart deployment
```

Now let's wait for our monkey pod to restart:
```bash,run
kubectl -n trading-1 get pods
```

And once it has restarted, let's describe it again:
```bash,run
kubectl -n trading-1 describe pod monkey
```

And now we can see OTel ENV vars being injected into the monkey pod. Let's check if we have APM data flowing in. Navigate to the [button label="Elastic"](tab-0) tab and click on `Applications` > `Service Inventory`. Ok cool, this is starting to look good.

# Why is router not showing up?

Click on the `trader` app and look at the `POST /trade/request` transaction. Scroll down to the bottom (trace samples) and look at the waterfall graph. Notice the broken trace. It looks like perhaps one of our applications is not being instrumented. Click on the `POST` span and look at `attributes.service.target.name`. Note that this `POST` is intended to target the `router` service, yet we don't see the `router` service in our Service Map.

Let's look at our `router` pod and see if we can figure out what's up.
```bash,run
kubectl -n trading-1 describe pod router
```

Huh. no OTel ENVs, even though the pod was restarted. Let's have a look at that deployment yaml. Click on the [button label="Code"](tab-2) button and examine the deployment yaml. Look at the deployment yaml for the `trader` service and compare it to the `router` service. Notice anything missing?

In order for the Operator to attach the correct APM agent, you need to apply an appropriate annotation to each pod. Note that the router pod is missing an annotation. Let's add it.  Navigate to the [button label="Source"](tab-2) tab and open `router.yaml`.

Uncomment the `instrumentation.opentelemetry.io/inject-nodejs` directive:

```
spec:
  template:
    metadata:
      annotations:
        instrumentation.opentelemetry.io/inject-nodejs: "opentelemetry-operator-system/elastic-instrumentation"
      ...
```
And then reapply the yaml:
```bash,run
./build.sh -d true -s router
```

Note that `router` was reconfigured:
```
deployment.apps/router configured
```

Wait for the `router` pod to restart:
```bash,run
kubectl -n trading-1 get pods
```

And once it has restarted, let's describe it again:
```bash,run
kubectl -n trading-1 describe pod router
```

And now it looks like our OTel ENVs are getting injected as expected. Let's check Elasticsearch.

Navigate to the [button label="Elastic"](tab-0) tab and click on `Applications` > `Service Inventory`. Note that we can now see a full distributed trace, as expected!

