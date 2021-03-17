# How to run locally
Requirements:
* Message broker (Google PubSub or RabbitMQ)
* MySQL database

# Configure PubSub service account

* In the https://console.cloud.google.com/ setup PubSub 
    * there is a useful documentation https://cloud.google.com/pubsub/docs/spring 
* Download a private key file with credentials as JSON file
* Add environment variable to execute microservices with GCP credentials: 
`GOOGLE_APPLICATION_CREDENTIALS=<path-to-credentials-file>.json`

# Run MySQL in docker container
There is `local/db/run-local-mysql.sh` script to launch MySQL in docker and create required databases.

# Import sample data
You can import sample data from the `local/db/data/*.csv` files into your db.
