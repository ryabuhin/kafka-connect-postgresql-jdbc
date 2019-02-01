package com.vriabukhin.connect.jdbc.sink;

import com.vriabukhin.connect.jdbc.sink.utils.C3P0DataSourceImpl;
import com.vriabukhin.connect.jdbc.sink.utils.DataSource;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

/**
 * PostgreSQL Sink Task Class.
 *
 * @author Valentyn Riabukhin.
 */
public class PostgreSQLSinkTask extends SinkTask {

    private static final Logger log = LoggerFactory.getLogger(PostgreSQLSinkTask.class);

    private PostgreSQLSinkConfig config;

    private JdbcDbWriter jdbcDbWriter;

    int remainingRetries; // init max retries via config.maxRetries ...

    @Override
    public void start(Map<String, String> props) {
        log.info("Starting PostgreSQL Sink task");
        this.config = new PostgreSQLSinkConfig(props);

        DataSource datasource = new C3P0DataSourceImpl.Builder()
                .withJdbcUrl(this.config.getString(PostgreSQLSinkConfig.CONNECTION_URL))
                .withUsername(this.config.getString(PostgreSQLSinkConfig.CONNECTION_USER))
                .withPassword(this.config.getPassword(PostgreSQLSinkConfig.CONNECTION_PASSWORD).toString())
                .withConnectionPoolSize(this.config.getInt(PostgreSQLSinkConfig.CONNECTION_DS_POOL_SIZE))
                .build();

        this.jdbcDbWriter = new JdbcDbWriter(this.config.getBoolean(PostgreSQLSinkConfig.INSERT_MODE_DATABASELEVEL), datasource);
    }

    @Override
    public void put(Collection<SinkRecord> records) {
        if (records.isEmpty()) {
            return;
        }
        final SinkRecord first = records.iterator().next();
        final int recordsCount = records.size();
        log.info("Received {} records. First record kafka coordinates:({}-{}-{}). Writing them to the database...",
                recordsCount, first.topic(), first.kafkaPartition(), first.kafkaOffset()
        );
        try {
            jdbcDbWriter.write(config.getString(PostgreSQLSinkConfig.TABLE_NAME_FORMAT), records);
        } catch (SQLException sqle) {
            log.warn("Write of {} records failed, remainingRetries={}", records.size(), remainingRetries, sqle);
        }
    }

    @Override
    public void stop() {
    }

    @Override
    public void flush(Map<TopicPartition, OffsetAndMetadata> map) {
        // Not necessary
    }

    @Override
    public String version() {
        return getClass().getPackage().getImplementationVersion();
    }
}
