package com.pj.Utils;

import com.pj.Config.RabbitMqConfig;
import com.rabbitmq.client.Connection;

public class ConnectionUtil {
    private static final RabbitMqConfig pool = new RabbitMqConfig(10, "localhost");

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
