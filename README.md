# Kafka Connect PostgreSQL JDBC Connector

---

* *mvn clean install && cp ./target/*.jar ./${KAFKA_CONNECT_CLASSPATH}/*
* restart kafka connector

Example of config file:

```
{
    "name": "jdbc-postgres-sink-connector",
    "config": {
        "connector.class": "com.vriabukhin.connect.jdbc.PostgreSQLSinkConnector",
        "tasks.max": "1",
        "topics": "some-topic",
        "connection.url": "jdbc:postgresql://127.0.0.1:5432/somedb",
        "connection.user": "postgres",
        "connection.password": "password",
        "connection.ds.pool.size": 5,
        "insert.mode.databaselevel": true,
        "table.name.format": "some_table"
}
```