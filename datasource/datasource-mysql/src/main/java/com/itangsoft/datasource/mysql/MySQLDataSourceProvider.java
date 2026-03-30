package com.itangsoft.datasource.mysql;

import com.itangsoft.datasource.core.spi.DataSourceProvider;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MySQLDataSourceProvider implements DataSourceProvider {

    @Override
    public String getType() {
        return "mysql8";
    }

    @Override
    public DataSource getDataSource(String url, String username, String password) {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);

        try (Connection connection = ds.getConnection()) {
            connection.getMetaData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 针对 MySQL 8.0 的特定配置可在此处设置
        return ds;
    }
}
