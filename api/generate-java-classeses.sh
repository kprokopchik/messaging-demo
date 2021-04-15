#!/bin/bash

set -e

services=("inventory-service" "order-service" "user-service")

for service in "${services[@]}"
do
  echo Generating for: $service
  rm -rf ./$service/target
  mkdir -p ./$service/target
  pushd ./$service/target
  ag -p view=provider \
    -p artifactType=application \
    ../asyncapi.json @asyncapi/java-spring-cloud-stream-template
  popd
done
