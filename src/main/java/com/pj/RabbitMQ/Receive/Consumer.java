package com.pj.RabbitMQ.Receive;

import com.pj.Utils.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Consumer {

    private static void receiveMessages(String exchangeName, String queueName, AMQP.BasicProperties props, String routingKey, Map<String, Object> headers) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName, props.getType());
        channel.queueDeclare(queueName, false, false, false, null);
        if (props.getType().equals("headers")) {
            channel.queueBind(queueName, exchangeName, routingKey, headers);
        } else {
            channel.queueBind(queueName, exchangeName, routingKey);
        }

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }

    // Nhận thông điệp với routing key (direct, topic, fanout exchange)
    public static void receiveMessagesWithRoutingKey(String exchangeName, String exchangeType, String queueName, String routingKey) throws Exception {
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().type(exchangeType).build();
        receiveMessages(exchangeName, queueName, props, routingKey, null);
    }

    // Nhận thông điệp với headers (headers exchange)
    public static void receiveMessagesWithHeaders(String exchangeName, String queueName, Map<String, Object> headers) throws Exception {
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().type("headers").build();
        receiveMessages(exchangeName, queueName, props, "", headers);
    }
}

