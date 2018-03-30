package com.sample.test.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by August.Zhou on 2017/7/27 17:46.
 */
public class CloseUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CloseUtils.class);

    public static void close(AutoCloseable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }

        } catch (Exception e) {
            LOGGER.error("error", e);
        }
    }

    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            LOGGER.error("error", e);
        }
    }

    public static void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (Exception e) {
            LOGGER.error("error", e);
        }
    }

    public static void close(ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (Exception e) {
            LOGGER.error("error", e);
        }
    }

    public static void close(Object object) {
        try {
            if (object != null && object instanceof AutoCloseable) {
                ((AutoCloseable) object).close();
            }
        } catch (Exception e) {
            LOGGER.error("error", e);
        }
    }

}
