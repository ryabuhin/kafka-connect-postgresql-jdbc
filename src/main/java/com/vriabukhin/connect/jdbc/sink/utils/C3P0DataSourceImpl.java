package com.vriabukhin.connect.jdbc.sink.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation for {@link DataSource} with c3p0.
 *
 * @author Valentine Riabukhin.
 */
public class C3P0DataSourceImpl implements DataSource {

    private static final Logger log = LoggerFactory.getLogger(C3P0DataSourceImpl.class);

    private ComboPooledDataSource cpds = new ComboPooledDataSource();

    private C3P0DataSourceImpl(final String jdbcUrl, final String username, final String password, final Integer poolSize) {
        try {
            cpds.setDriverClass("org.postgresql.Driver");
            cpds.setJdbcUrl(jdbcUrl);
            cpds.setUser(username);
            cpds.setPassword(password);
            cpds.setInitialPoolSize(poolSize);
        } catch (PropertyVetoException e) {
            log.info("unknown jdbcurl || username || password");
        }
    }

    public Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }

    public static class Builder {

        private String jdbcUrl;

        private String username;

        private String password;

        private Integer poolSize;

        public Builder withJdbcUrl(final String jdbcUrl) {
            this.jdbcUrl = jdbcUrl;
            return this;
        }

        public Builder withUsername(final String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(final String password) {
            this.password = password;
            return this;
        }

        public Builder withConnectionPoolSize(final Integer poolSize) {
            this.poolSize = poolSize;
            return this;
        }

        public C3P0DataSourceImpl build() {
            return new C3P0DataSourceImpl(this.jdbcUrl, this.username, this.password, this.poolSize);
        }
    }

}
