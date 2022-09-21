# CSV-Task

## Actions (Mac OS)
### Running PostgreSQL
1. Pull Docker Image
   `docker pull postgres`

2. Build data directory
   `mkdir -p ~/srv/postgres`

3. Run docker image
   `docker run --rm --name postgres-db -e POSTGRES_PASSWORD=password -d -v $HOME/srv/postgres:/var/lib/postgresql/data -p 5432:5432 postgres`

### Stopping PostgreSQL
`docker stop postgres-db`

### Logging into Database
* `psql -h localhost -U postgres -d bank`

### Creating starter data
* `psql -h localhost -U postgres -f database.sql`

### Running Postman
1. Import attached `csv-handler.postman_collection.json` file
2. You may use attached `test-file.csv` file to upload
