package com.pj.Config;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeoutException;

public class RabbitMqConfig {
    private final BlockingQueue<Connection> pool;
    private final ConnectionFactory factory;

    public RabbitMqConfig(int maxPoolSize, String host) {
        this.pool = new LinkedBlockingQueue<>(maxPoolSize);
        this.factory = new ConnectionFactory();
        this.factory.setHost(host);
        for (int i = 0; i < maxPoolSize; i++) {
            try {
                Connection connection = factory.newConnection();
                if (!pool.offer(connection)) {
                    throw new RuntimeException("Failed to add RabbitMQ connection to the pool");
                }
            } catch (IOException | TimeoutException e) {
                throw new RuntimeException("Failed to create initial RabbitMQ connections", e);
            }
        }
    }

    public Connection getConnection() throws InterruptedException {
        Connection connection = pool.take();
        if (!connection.isOpen()) {
            try {
                connection = factory.newConnection();
            } catch (IOException | TimeoutException e) {
                throw new RuntimeException("Failed to create new RabbitMQ connection", e);
            }
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection != null && connection.isOpen()) {
            if (!pool.offer(connection)) {
                throw new RuntimeException("Failed to add RabbitMQ connection to the pool");
            }
        }
    }

    public void close() {
        for (Connection connection : pool) {
            try {
                connection.close();
            } catch (IOException e) {
                throw new RuntimeException("Failed to close RabbitMQ connection", e);
            }
        }
    }
}
