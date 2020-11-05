#!/bin/bash

docker-compose up --build -d
cd migration
./run_migrations.sh