package com.itangsoft.datasource.oracle;

import com.itangsoft.datasource.core.spi.DataSourceProvider;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class OracleDataSourceProvider implements DataSourceProvider {

    @Override
    public String getType() {
        return "oracle";
    }

    @Override
    public DataSource getDataSource(String url, String username, String password) {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        // 针对 Oracle 的特定配置
        return ds;
    }
}
