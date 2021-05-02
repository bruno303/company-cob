#!/bin/bash

mvn clean install \
    -DskipTests \
    -Dimage-version=1.0.0 \
    -Pdocker