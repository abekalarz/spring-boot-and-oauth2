#!/bin/bash

docker rm $(docker stop $(docker ps -a -q --filter ancestor=mongo:3.6 --format="{{.ID}}"))
docker rm $(docker stop $(docker ps -a -q --filter ancestor=docker_spring_build_and_run --format="{{.ID}}"))