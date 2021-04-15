#!/bin/bash 

set -e

CONTAINER_NAME=event-driven-mysql-local
MYSQL_ROOT_PASSWORD=root

docker run --name $CONTAINER_NAME \
 -e MYSQL_ROOT_PASSWORD=$MYSQL_ROOT_PASSWORD \
 -p 3306:3306 \
 -d mysql

while ! docker exec $CONTAINER_NAME mysqladmin --user=root --password=$MYSQL_ROOT_PASSWORD --host "127.0.0.1" ping --silent &> /dev/null ; do
    echo "Waiting for database connection..."
    sleep 2
done

SQL="CREATE DATABASE IF NOT EXISTS inventory_db; \
CREATE DATABASE IF NOT EXISTS order_db; \
CREATE DATABASE IF NOT EXISTS user_db;"

docker exec -e MYSQL_PWD=$MYSQL_ROOT_PASSWORD $CONTAINER_NAME mysql --user=root --execute="$SQL" --protocol=tcp
