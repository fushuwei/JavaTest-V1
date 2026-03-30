package com.itangsoft.datasource.mysql5;

import com.itangsoft.datasource.core.spi.DataSourceProvider;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class MySQL5DataSourceProvider implements DataSourceProvider {

    @Override
    public String getType() {
        return "mysql51";
    }

    @Override
    public DataSource getDataSource(String url, String username, String password) {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        // 针对 MySQL 5.1 的特定配置可在此处设置
        return ds;
    }
}
