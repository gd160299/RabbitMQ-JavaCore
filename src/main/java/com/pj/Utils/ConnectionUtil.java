package com.pj.Utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {
    private ConnectionUtil() {}

    private static class SingletonHelper {
        private static final Connection connection = createConnection();
    }

    private static Connection createConnection() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            return factory.newConnection();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create RabbitMQ connection", e);
        }
    }

    public static Connection getConnection() {
        return SingletonHelper.connection;
    }
}
