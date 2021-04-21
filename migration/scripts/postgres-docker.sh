docker run --name companycob-postgres \
      -p "5432:5432" \
      -e POSTGRES_PASSWORD=password \
      -e POSTGRES_DB=companycob \
      -d \
      postgres:13-alpine

## Utils - maybe
# mvn liquibase:diff liquibase:update