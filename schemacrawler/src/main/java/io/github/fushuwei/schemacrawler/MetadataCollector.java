package io.github.fushuwei.schemacrawler;

import schemacrawler.schema.Catalog;
import schemacrawler.schema.Column;
import schemacrawler.schema.ForeignKey;
import schemacrawler.schema.Schema;
import schemacrawler.schema.Table;
import schemacrawler.schemacrawler.SchemaCrawlerOptions;
import schemacrawler.schemacrawler.SchemaCrawlerOptionsBuilder;
import schemacrawler.tools.utility.SchemaCrawlerUtility;
import us.fatehi.utility.datasource.DatabaseConnectionSource;
import us.fatehi.utility.datasource.DatabaseConnectionSources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;

public class MetadataCollector {

    public static void main(String[] args) throws Exception {
        // 1. 配置数据库连接
        String url = "jdbc:mysql://localhost:3306/nacos";
        try (Connection connection = DriverManager.getConnection(url, "root", "123456")) {

            // 2. 配置SchemaCrawler选项
            SchemaCrawlerOptions options = SchemaCrawlerOptionsBuilder.newSchemaCrawlerOptions();

            // 3. 执行采集，获取Catalog对象（这是核心数据载体）
            DatabaseConnectionSource connectionSource = DatabaseConnectionSources.fromConnection(connection);
            Catalog catalog = SchemaCrawlerUtility.getCatalog(connectionSource, options);

            // 4. 遍历并处理元数据
            for (Schema schema : catalog.getSchemas()) {
                System.out.println("Schema: " + schema.getName());
                Collection<Table> tables = catalog.getTables(schema);
                for (Table table : tables) {
                    System.out.println("  Table: " + table.getName());
                    // 获取列信息
                    for (Column column : table.getColumns()) {
                        System.out.println("    Column: " + column.getName()
                            + " (" + column.getColumnDataType() + ")");
                    }
                    // 获取主键信息
                    if (table.getPrimaryKey() != null) {
                        System.out.println("    Primary Key: " + table.getPrimaryKey());
                    }
                    // 获取外键信息
                    for (ForeignKey foreignKey : table.getForeignKeys()) {
                        System.out.println("    Foreign Key: " + foreignKey);
                    }
                }
            }
        }
    }
}
