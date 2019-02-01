package com.vriabukhin.connect.jdbc.sink;

import com.vriabukhin.connect.jdbc.sink.utils.DataSource;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.sink.SinkRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

/**
 * JDBC Database Writer.
 *
 * @author Valentine Riabukhin.
 */
public class JdbcDbWriter {

    private final boolean isInsertModeDatabaseLevelEnabled;

    private static final Logger log = LoggerFactory.getLogger(PostgreSQLSinkTask.class);

    private final DataSource dataSource;

    private static final String INSERT_STATEMENT = "INSERT INTO %s(%s) VALUES (%s)";

    protected JdbcDbWriter(final boolean insertModeDatabaseLevel, final DataSource dataSource) {
        this.isInsertModeDatabaseLevelEnabled = insertModeDatabaseLevel;
        this.dataSource = dataSource;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnf) {
            log.error("PostgreSQL JDBC driver not found", cnf);
        }
    }

    protected void write(final String tableNameFormat, final Collection<SinkRecord> records) throws SQLException {
        // tableNameFormat must be processed
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            if (isInsertModeDatabaseLevelEnabled) {
                Statement statement = connection.createStatement();
                records.forEach(e -> {
                    final String fieldsAsString = arrayFieldNamesSplitAsString(e.valueSchema().fields());
                    final String valuesAsString = arrayFieldValuesSplitAsString((Struct) e.value(), e.valueSchema());
                    try {
                        final String finalQuery = String.format(INSERT_STATEMENT, tableNameFormat, fieldsAsString, valuesAsString);
                        log.info("Final prepared statement: '{}' //", finalQuery);
                        statement.addBatch(finalQuery);
                    } catch (SQLException ex1) {
                        ex1.printStackTrace();
                    }
                });
                statement.executeBatch();
            } else {
                records.forEach(e -> {
                    // do some logic - database level disabled
                });
            }

            connection.commit();
        }
    }

    private String arrayFieldNamesSplitAsString(List<Field> array) {
        final StringBuilder strBuilder = new StringBuilder();
        array.forEach(f -> strBuilder.append(f.name()).append(","));
        strBuilder.deleteCharAt(strBuilder.length() - 1); // delete the last coma ',' char
        return strBuilder.toString();
    }

    private String arrayFieldValuesSplitAsString(Struct valueStruct, Schema valueSchema) {
        final StringBuilder strBuilder = new StringBuilder();
        valueSchema.fields().forEach(f -> strBuilder.append(wrapInQuotesIfNeeded(f.schema().type(), valueStruct.get(f).toString())).append(","));
        strBuilder.deleteCharAt(strBuilder.length() - 1); // delete the last coma ',' char
        return strBuilder.toString();
    }

    private String wrapInQuotesIfNeeded(final Schema.Type schemaType, final String str) {
        return schemaType == Schema.Type.STRING ? "'" + str + "'" : str;
    }

}