#!/bin/bash

docker run --rm --network companycob_companycobnet -v "$PWD:/liquibase/changelog" liquibase/liquibase:4.1.1 --driver=org.postgresql.Driver --url="jdbc:postgresql://company-cob-postgre-database:5432/companycob" --changeLogFile=db/db.changelog-master.xml --username=postgres --password=password --logLevel=DEBUG update
