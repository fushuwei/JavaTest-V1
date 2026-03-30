package com.itangsoft.datasource.service;

import com.itangsoft.datasource.core.spi.DataSourceProvider;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSourceContext {

    private static final Map<String, DataSourceProvider> providers = new HashMap<>();

    public static void setProviders(List<DataSourceProvider> list) {
        providers.clear();
        for (DataSourceProvider provider : list) {
            providers.put(provider.getType(), provider);
        }
    }

    /**
     * 根据类型获取数据源实例
     *
     * @param type     数据源类型，例如 "mysql51"、"mysql80"、"oracle" 等
     * @param url      数据库连接 URL
     * @param username 用户名
     * @param password 密码
     * @return 数据源实例
     */
    public static DataSource getDataSource(String type, String url, String username, String password) {
        DataSourceProvider provider = providers.get(type);
        if (provider == null) {
            throw new IllegalArgumentException("不支持的数据源类型: " + type);
        }
        return provider.getDataSource(url, username, password);
    }
}

