package com.pj.RabbitMQ.Send;

import com.pj.Utils.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Producer {
    private static final Logger logger = LogManager.getLogger(Producer.class);

    private static void sendMessage(String exchangeName, String message, AMQP.BasicProperties props, String routingKey, boolean isPublisherConfirms) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = null;
        try {
            channel = connection.createChannel();
            logger.info("Begin: Declaring exchange: {}", exchangeName);
            channel.exchangeDeclare(exchangeName, props.getType());

            if (isPublisherConfirms) {
                channel.confirmSelect();
            }
            channel.basicPublish(exchangeName, routingKey, props, message.getBytes(StandardCharsets.UTF_8));
            logger.info("Message sent: {}", message);
            if (isPublisherConfirms) {
                if (channel.waitForConfirms()) {
                    logger.info("Message confirmed by RabbitMQ");
                } else {
                    logger.error("Message not confirmed by RabbitMQ");
                }
            }
        } finally {
            if(channel != null && channel.isOpen()) {
                channel.close();
            }
            ConnectionUtil.releaseConnection(connection);
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

