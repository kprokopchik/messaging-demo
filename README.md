# How to run locally
Requirements:
* Message broker (Google PubSub or RabbitMQ)
* MySQL database

# Use PubSub Emulator locally
You may inspect `local/pubsub/Dockerfile` file to get some information regarding the docker image which provides PubSub emulator.
There are tiny scripts to help you:
* `local/pubsub/build.sh` script to build docker image with PubSub emulator
* `local/pubsub/run.sh` script to run the PubSub emulator in docker container
### Configute SpringBoot to use PubSub emulator 
To use PubSub emulator with your application you should provide the following application property via application.yml 
```
spring:
  cloud:
    gcp:
      pubsub:
        emulator-host: localhost:8085
```
or application.properties
```
spring.cloud.gcp.pubsub.emulator-host=localhost:8085
```
For any other application types (non Spring Boot) you should provide `PUBSUB_EMULATOR_HOST` environment variable.
Example:
```
export PUBSUB_EMULATOR_HOST=localhost:8085
```

# Configure PubSub service account to use GCP service
In case if you want to use cloud provider (GCP) service then please perform the following steps:
* In the https://console.cloud.google.com/ setup PubSub 
    * there is a useful documentation https://cloud.google.com/pubsub/docs/spring 
* Download a private key file with credentials as JSON file
* Add environment variable to execute microservices with GCP credentials: 
`GOOGLE_APPLICATION_CREDENTIALS=<path-to-credentials-file>.json`
* Remove emulator reference from the application properties: `spring.cloud.gcp.pubsub.emulator-host=localhost:8085`

# Run MySQL in docker container
There is `local/db/run-local-mysql.sh` script to launch MySQL in docker and create required databases.

### Import sample data to the DB
You can import sample data from the `local/db/data/*.csv` files into your db.
