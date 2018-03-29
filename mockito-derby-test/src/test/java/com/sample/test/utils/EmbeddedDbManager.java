package com.sample.test.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 嵌入式数据库管理，负责DB的初始化 Created by August.Zhou on 2017/1/3 13:54.
 */
public class EmbeddedDbManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedDbManager.class);
    private static final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String url = "jdbc:derby:memory:myDB;create=true";
    //    private static final String driver = "org.hsqldb.jdbc.JDBCDriver";
//    private static final String url = "jdbc:hsqldb:mem:mymemdb";
    private static volatile boolean isDbInited = false;
    private static Connection connection = null;

//    static {
//        Constants.setIsInTest(true);
//    }

    private static final void initDb() {
        if (isDbInited) {
            return;
        }
        synchronized (LOGGER) {
            if (isDbInited) {
                return;
            }
            initDb0();
        }

    }

    private static final void initDb0() {
        try {
            Class.forName(driver);
        } catch (Exception e) {
            LOGGER.error("LOAD DRIVER ERROR", e);
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            LOGGER.error("Connected to and created database", e);
        }
        isDbInited = true;
    }

    public static synchronized final boolean initTable(String name) {
        InputStream inputStream = null;
        Statement statement = null;
        try {
            initDb();
            inputStream = Thread.class.getResourceAsStream("/createTableSql/" + name + ".txt");
            String text = IOUtils.toString(inputStream).trim();
            statement = connection.createStatement();
            int c = statement.executeUpdate(text);
            LOGGER.info("create table {} result {}", name, c);
        } catch (Exception e) {
            LOGGER.error("createSQL: \n" + name, e);
            return false;
        } finally {
            CloseUtils.close(inputStream);
            CloseUtils.close(statement);
        }
        return true;
    }

    public static synchronized final boolean dropTable(String name) {
        Statement statement = null;
        try {
            initDb();
            statement = connection.createStatement();
            int c = statement.executeUpdate("drop table " + name);
            LOGGER.info("drop table {} result {}", name, c);
        } catch (Exception e) {
            LOGGER.error("createSQL: \n" + name, e);
            return false;
        } finally {
            CloseUtils.close(statement);
        }
        return true;
    }

    public static void main(String[] args) throws SQLException, IOException {

        // 创建Supplier表
//		InputStream Supplier = Thread.class.getResourceAsStream("/createTableSql/Supplier.txt");
//		String SupplierStr = IOUtils.toString(Supplier).trim();
        EmbeddedDbManager.initTable("UserInfo");

    }

}
