# Requirements
* npm

# Install AsyncAPI Generator
```
npm install -g @asyncapi/generator
```

# AsyncAPI Generator templates
https://github.com/asyncapi/generator

Available generator templates:
* Java Spring service
* **Java Spring Cloud Stream service**
* Nodejs service that uses Hermes package
* Nodejs service that supports WebSockets protocol only
* Python service that uses Paho library
* HTML documentation
* Markdown documentation
* TypeScript NATS client

# Generate Java classes
Manual: https://github.com/asyncapi/java-spring-cloud-stream-template 

```
ag -p view=provider \
../asyncapi.json @asyncapi/java-spring-cloud-stream-template
```

# Generate documentation
```
ag order-service/asyncapi.json @asyncapi/markdown-template
```
