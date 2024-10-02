package com.pj.utils;

import com.pj.config.RabbitMqConfig;
import com.rabbitmq.client.Connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConnectionUtil {
    private static final int maxConnections;
    private static final String host;

    static {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("./config/config.properties")) {
            properties.load(input);
            maxConnections = Integer.parseInt(properties.getProperty("rabbitmq.max.connections", "10"));
            host = properties.getProperty("rabbitmq.host", "localhost");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file", e);
        }
    }

    private static final RabbitMqConfig pool = new RabbitMqConfig(maxConnections, host);

    public static Connection getConnection() throws InterruptedException {
        return pool.getConnection();
    }

    public static void releaseConnection(Connection connection) {
        pool.releaseConnection(connection);
    }

    public static void closePool() {
        pool.close();
    }
}
