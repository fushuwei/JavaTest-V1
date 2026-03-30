package com.itangsoft.datasource.mongodb;

import com.itangsoft.datasource.core.spi.DataSourceProvider;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import javax.sql.DataSource;

public class MongoDBDataSourceProvider implements DataSourceProvider {

    @Override
    public String getType() {
        return "mongodb";
    }

    @Override
    public DataSource getDataSource(String url, String username, String password) {
        // 这里仅作为示例，MongoDB 实际上不适用 javax.sql.DataSource
        System.out.println("创建 MongoDB 客户端，URL：" + url);
        MongoClient client = MongoClients.create(url);
        return null; // 返回 null 或自行封装为 DataSource 形式
    }
}
