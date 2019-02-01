package com.vriabukhin.connect.jdbc.sink.utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * DataSource Interface.
 *
 * @author Valentine Riabukhin.
 */
public interface DataSource {

    Connection getConnection() throws SQLException;

}
