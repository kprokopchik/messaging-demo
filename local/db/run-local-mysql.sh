#!/bin/bash 

set -e

CONTAINER_NAME=event-driven-mysql-local
MYSQL_ATTACH_DATA_PATH=/tmp/event-driven-mysql
MYSQL_ROOT_PASSWORD=root

mkdir -p $MYSQL_ATTACH_DATA_PATH

docker run --name $CONTAINER_NAME \
 -v $MYSQL_ATTACH_DATA_PATH:/var/lib/mysql \
 -e MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASSWORD \
 -p 3306:3306 \
 -d mysql

SQL="CREATE DATABASE IF NOT EXISTS inventory_db; \
CREATE DATABASE IF NOT EXISTS order_db; \
CREATE DATABASE IF NOT EXISTS user_db;"

docker exec -e MYSQL_PWD=$MYSQL_ROOT_PASSWORD $CONTAINER_NAME mysql --user=root --execute="$SQL"
