# Prerequisites:
* Message broker (Google PubSub)
  * [Run PubSub Emulator locally](#run-pubsub-emulator-locally)
* MySQL database
  * [Run MySQL locally](#run-mysql-in-docker-container)
  * [Import sample data to the DB](#import-sample-data-to-the-db)

# Run PubSub Emulator locally
You may inspect `local/pubsub/Dockerfile` file to get some information regarding the docker image which provides PubSub emulator.
To run PubSub in a container execute the following commands:
```
# Go to the folder containing Dockerfile
cd local/pubsub

# Build an image with PubSub emulator: 
docker build -t pubsub-local .

# Run the PubSub emulator in a container: 
docker run -p "8085:8085" --name pubsub_emulator -d pubsub-local
```
This will give you a PubSub service available on the 8085 port, so you are able to connect to it using `localhost:8085` address.

# Configure SpringBoot to use PubSub emulator
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
Execute the following script to launch MySQL in Docker and to create required databases:
```
local/db/run-local-mysql.sh
```
The MySQL server will be launched at the default 3306 port, and the root user password will be `root`.

### Import sample data to the DB
You may use sample data from the `local/db/data/*.csv` files for import into your DB, so you will have something to start with.
