#!/bin/bash

cp -r ../src/main/resources/mongo/dump/ mongo-import/dump/
docker-compose --verbose up