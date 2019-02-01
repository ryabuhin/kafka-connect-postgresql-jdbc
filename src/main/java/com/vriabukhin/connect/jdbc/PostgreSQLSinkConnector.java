package com.vriabukhin.connect.jdbc;

import com.vriabukhin.connect.jdbc.sink.PostgreSQLSinkConfig;
import com.vriabukhin.connect.jdbc.sink.PostgreSQLSinkTask;
import org.apache.kafka.common.config.Config;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * PostgreSQL Sink Connector Implementation.
 *
 * @author Valentine Riabukhin.
 */
public class PostgreSQLSinkConnector extends SinkConnector {

    private static final Logger log = LoggerFactory.getLogger(PostgreSQLSinkConnector.class);

    private Map<String, String> configProps;

    @Override
    public void start(Map<String, String> props) {
        configProps = props;
    }

    @Override
    public Class<? extends Task> taskClass() {
        return PostgreSQLSinkTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        log.info("Setting task configurations for {} workers.", maxTasks);
        final List<Map<String, String>> configs = new ArrayList<>(maxTasks);
        for (int i = 0; i < maxTasks; ++i) {
            configs.add(configProps);
        }
        log.info("Setting task configurations for {} workers completed / return", maxTasks);
        return configs;
    }

    @Override
    public void stop() {
    }

    @Override
    public ConfigDef config() {
        return PostgreSQLSinkConfig.CONFIG_DEF;
    }

    @Override
    public Config validate(Map<String, String> connectorConfigs) {
        // TODO cross-fields validation here: pkFields against the pkMode
        return super.validate(connectorConfigs);
    }

    @Override
    public String version() {
        return "0.0.1-SNAPSHOT";
    }
}