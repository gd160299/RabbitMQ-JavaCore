package com.pj.RabbitMQ.Send;

import com.pj.Utils.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Producer {

    private static void sendMessage(String exchangeName, String message, AMQP.BasicProperties props, String routingKey, boolean isPublisherConfirms) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        try (Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, props.getType());

            if (isPublisherConfirms) {
                channel.confirmSelect();
            }
            channel.basicPublish(exchangeName, routingKey, props, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
            if (isPublisherConfirms) {
                if (channel.waitForConfirms()) {
                    System.out.println(" [x] Message confirmed by RabbitMQ.");
                } else {
                    System.out.println(" [x] Message not confirmed.");
                }
            }
        }
    }

    // Gửi thông điệp với routing key (direct, topic, fanout exchange)
    public static void sendMessageWithRoutingKey(String exchangeName, String message, String exchangeType, String routingKey, boolean isPublisherConfirms) throws Exception {
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().type(exchangeType).build();
        sendMessage(exchangeName, message, props, routingKey, isPublisherConfirms);
    }

    // Gửi thông điệp với headers (headers exchange)
    public static void sendMessageWithHeaders(String exchangeName, String message, Map<String, Object> headers, boolean isPublisherConfirms) throws Exception {
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                .headers(headers)
                .type("headers")
                .build();
        sendMessage(exchangeName, message, props, "", isPublisherConfirms);
    }
}

