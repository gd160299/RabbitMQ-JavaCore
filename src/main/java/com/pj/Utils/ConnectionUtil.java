package com.pj.Utils;

import com.pj.Config.RabbitMqConfig;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeoutException;

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
