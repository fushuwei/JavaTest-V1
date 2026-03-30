package com.itangsoft.datasource.core.spi;

import javax.sql.DataSource;

public interface DataSourceProvider {

    /**
     * 返回数据源的类型标识，例如 "mysql5"、"mysql8"、"oracle"、"mongodb"
     */
    String getType();

    /**
     * 根据传入的连接参数创建 DataSource 实例
     *
     * @param url      数据库连接 URL
     * @param username 用户名
     * @param password 密码
     * @return 数据源实例
     */
    DataSource getDataSource(String url, String username, String password);
}
