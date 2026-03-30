package com.itangsoft.datasource.service;

import com.itangsoft.datasource.core.spi.DataSourceProvider;

import java.util.List;

// @SpringBootApplication
public class DatasourceServiceApplication {

    public static void main(String[] args) {
        // SpringApplication.run(DatasourceServiceApplication.class, args);

        // 从插件目录加载所有 DataSourceProvider 实现
        String pluginsDir = System.getProperty("user.dir") + "/datasource/plugins";
        List<DataSourceProvider> providers = DatasourcePluginLoader.loadPlugins(pluginsDir);
        System.out.println("成功加载的数据源插件：");
        for (DataSourceProvider provider : providers) {
            System.out.println(" - " + provider.getType());
            provider.getDataSource("jdbc:mysql://192.168.10.142:30316", "root", "PANGUmysql2023:)");
        }

        // 可以将加载的 providers 存入 DataSourceContext 中，便于后续使用
        DataSourceContext.setProviders(providers);
    }
}
