package com.itangsoft.datasource.service;

import com.itangsoft.datasource.core.spi.DataSourceProvider;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;

/**
 * 插件加载器：从指定的插件目录中加载 jar 包，并利用 SPI 加载 DataSourceProvider 实现。
 */
public class DatasourcePluginLoader {

    /**
     * 从指定目录加载所有插件（jar 文件），并返回加载到的 DataSourceProvider 实例列表。
     *
     * @param pluginsDirPath 插件目录路径，例如 "./plugins"
     * @return DataSourceProvider 实例列表
     */
    public static List<DataSourceProvider> loadPlugins(String pluginsDirPath) {
        List<DataSourceProvider> providers = new ArrayList<>();
        File pluginsDir = new File(pluginsDirPath);
        if (!pluginsDir.exists() || !pluginsDir.isDirectory()) {
            System.err.println("插件目录不存在: " + pluginsDirPath);
            return providers;
        }
        File[] files = pluginsDir.listFiles(File::isDirectory);
        if (files != null) {
            for (File file : files) {
                try {
                    List<URL> urls = new ArrayList<>();
                    for (File jar : Objects.requireNonNull(file.listFiles((dir, name) -> name.endsWith(".jar")))) {
                        urls.add(jar.toURI().toURL());
                    }
                    // 为每个插件创建独立的类加载器
                    URLClassLoader childClassLoader = new URLClassLoader(urls.toArray(new URL[0]), DatasourcePluginLoader.class.getClassLoader());
                    ServiceLoader<DataSourceProvider> loader = ServiceLoader.load(DataSourceProvider.class, childClassLoader);
                    for (DataSourceProvider provider : loader) {
                        providers.add(provider);
                        System.out.println("加载到插件: " + provider.getType() + " (来自 " + file.getName() + ")");

                        Thread.currentThread().setContextClassLoader(childClassLoader);
                        provider.getDataSource("jdbc:mysql://192.168.10.142:30316", "root", "PANGUmysql2023:)");
                    }
                } catch (Exception e) {
                    System.err.println("加载插件失败: " + file.getName());
                    e.printStackTrace();
                }
            }
        }

        return providers;
    }
}
