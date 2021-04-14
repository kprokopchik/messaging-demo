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

# Run MySQL in docker container
Execute the following script to launch MySQL in Docker and to create required databases:
```
local/db/run-local-mysql.sh
```
The MySQL server will be launched at the default 3306 port, and the root user password will be `root`.

### Import sample data to the DB
You may use sample data from the `local/db/data/*.csv` files for import into your DB, so you will have something to start with.
