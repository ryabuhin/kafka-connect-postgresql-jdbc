package com.vriabukhin.connect.jdbc.sink;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

/**
 * PostgreSQL Sink Configuration Class.
 *
 * @author Valentyn Riabukhin.
 */
public class PostgreSQLSinkConfig extends AbstractConfig {

    static final String CONNECTION_URL = "connection.url";
    private static final String CONNECTION_URL_DOC = "JDBC connection URL.";
    private static final String CONNECTION_URL_DISPLAY = "JDBC URL";

    static final String CONNECTION_USER = "connection.user";
    private static final String CONNECTION_USER_DOC = "JDBC connection user.";
    private static final String CONNECTION_USER_DISPLAY = "JDBC User";

    static final String CONNECTION_PASSWORD = "connection.password";
    private static final String CONNECTION_PASSWORD_DOC = "JDBC connection password.";
    private static final String CONNECTION_PASSWORD_DISPLAY = "JDBC Password";

    private static final String CONNECTION_GROUP = "Connection";

    static final String CONNECTION_DS_POOL_SIZE = "connection.ds.pool.size";
    private static final String CONNECTION_DS_POOL_SIZE_DOC = "JDBC datasource connection pool size.";
    private static final String CONNECTION_DS_POOL_SIZE_DISPLAY = "JDBC datasource connection pool size";

    static final String TABLE_NAME_FORMAT = "table.name.format";
    private static final String TABLE_NAME_FORMAT_DOC = "Table name format to insert.";
    private static final String TABLE_NAME_FORMAT_DISPLAY = "Table format name";

    static final String INSERT_MODE_DATABASELEVEL = "insert.mode.databaselevel";
    private static final String INSERT_MODE_DATABASELEVEL_DOC = "Insert mode database level.";
    private static final String INSERT_MODE_DATABASELEVEL_DISPLAY = "Insert mode database level";

    public static final ConfigDef CONFIG_DEF = new ConfigDef()
            .define(
                    CONNECTION_URL,
                    ConfigDef.Type.STRING,
                    ConfigDef.NO_DEFAULT_VALUE,
                    ConfigDef.Importance.HIGH,
                    CONNECTION_URL_DOC,
                    CONNECTION_GROUP,
                    1,
                    ConfigDef.Width.LONG,
                    CONNECTION_URL_DISPLAY
            )
            .define(
                    CONNECTION_USER,
                    ConfigDef.Type.STRING,
                    null,
                    ConfigDef.Importance.HIGH,
                    CONNECTION_USER_DOC,
                    CONNECTION_GROUP,
                    2,
                    ConfigDef.Width.MEDIUM,
                    CONNECTION_USER_DISPLAY
            )
            .define(
                    CONNECTION_PASSWORD,
                    ConfigDef.Type.PASSWORD,
                    null,
                    ConfigDef.Importance.HIGH,
                    CONNECTION_PASSWORD_DOC,
                    CONNECTION_GROUP,
                    3,
                    ConfigDef.Width.MEDIUM,
                    CONNECTION_PASSWORD_DISPLAY
            )
            .define(
                    TABLE_NAME_FORMAT,
                    ConfigDef.Type.STRING,
                    ConfigDef.NO_DEFAULT_VALUE,
                    ConfigDef.Importance.HIGH,
                    TABLE_NAME_FORMAT_DOC,
                    CONNECTION_GROUP,
                    4,
                    ConfigDef.Width.LONG,
                    TABLE_NAME_FORMAT_DISPLAY
            )
            .define(
                    CONNECTION_DS_POOL_SIZE,
                    ConfigDef.Type.INT,
                    1,
                    ConfigDef.Importance.LOW,
                    CONNECTION_DS_POOL_SIZE_DOC,
                    CONNECTION_GROUP,
                    5,
                    ConfigDef.Width.MEDIUM,
                    CONNECTION_DS_POOL_SIZE_DISPLAY
            ).define(
                    INSERT_MODE_DATABASELEVEL,
                    ConfigDef.Type.BOOLEAN,
                    false,
                    ConfigDef.Importance.LOW,
                    INSERT_MODE_DATABASELEVEL_DOC,
                    CONNECTION_GROUP,
                    6,
                    ConfigDef.Width.MEDIUM,
                    INSERT_MODE_DATABASELEVEL_DISPLAY
            );

    protected PostgreSQLSinkConfig(final Map<?, ?> props) {
        super(CONFIG_DEF, props);
    }

}
