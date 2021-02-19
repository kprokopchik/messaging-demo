#!/bin/bash 

set -e

CONTAINER_NAME=event-driven-rabbitmq-local
RABBITMQ_DEFAULT_USER=rabbit
RABBITMQ_DEFAULT_PASS=rabbit

docker run --name $CONTAINER_NAME \
 -e RABBITMQ_DEFAULT_USER=$RABBITMQ_DEFAULT_USER \
 -e RABBITMQ_DEFAULT_PASS=$RABBITMQ_DEFAULT_PASS \
 -p 5672:5672 \
 -p 15672:15672 \
 -d rabbitmq:3-management

# Management console available on http://localhost:15672 with the default username and password of guest / guest
