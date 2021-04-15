To use PubSub emulator you should provide application property via application.yml 
```
spring:
  cloud:
    gcp:
      pubsub:
        emulator-host: "localhost:8085"
```
or application.properties
```
spring.cloud.gcp.pubsub.emulator-host=localhost:8085
```

If you're not using SpringBoot then provide `PUBSUB_EMULATOR_HOST` environment variable to the application.
Example:
```
export PUBSUB_EMULATOR_HOST=localhost:8085
```

