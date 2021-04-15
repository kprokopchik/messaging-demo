# Prerequisites:
* Docker
    * Docker will be used to run PubSub Emulator and MySQL server in containers
* Message broker (Google PubSub)
    * [Run PubSub Emulator in Docker](#run-pubsub-emulator-in-docker-container)
* MySQL database
    * [Run MySQL in Docker](#run-mysql-in-docker-container)
* npm
    * [Install npm on macOS](#install-npm-on-macos)
    * [Install npm on Windows](#install-npm-on-windows)
* Compile application
    * [Build and run demo application](#build-and-run-demo-application)


# Run PubSub Emulator in Docker container
You may inspect `local/pubsub/Dockerfile` file to get some information regarding the docker image which provides PubSub emulator.
To run PubSub in a container execute the following commands:
```shell
# Go to the folder containing Dockerfile
cd local/pubsub

# Build an image with PubSub emulator: 
docker build -t pubsub-local .

# Run the PubSub emulator in a container: 
docker run -p "8085:8085" --name pubsub_emulator -d pubsub-local
```
This will give you a PubSub service available on the 8085 port, so you are able to connect to it using `localhost:8085` address. 

# Run MySQL in Docker container
Execute the following script to launch MySQL in Docker and to create required databases:
```
local/db/run-local-mysql.sh
```
The MySQL server will be launched at the default 3306 port, and the root user password will be `root`.

# Install npm 
You will need npm installed to use AsyncAPI Generator

### Install npm on macOS
```
brew install npm 
```

### Install npm on Windows
Follow the link: https://nodejs.org/en/download/

# Build and run demo application
There are 3 microservices for the demo purposes: 
* Inventory (on port `8081`)
* Order (on port `8082`)
* User (on port `8083`)

Each microservice intended to use only its own codebase, so they could be developed independently.
Please build and run at least one application to be sure the local environment is ready for hands-on session.
```shell
cd apps/inventory
./gradlew bootRun
```
If application starts successfully then the Swagger UI is available (e.g. for Inventory it is expected at http://localhost:8081/swagger-ui.html)
