package com.pj.ExchangeDemo;

import com.pj.RabbitMQ.Receive.Consumer;
import com.pj.RabbitMQ.Send.Producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultExchangeDemo {

    private static final Logger logger = LogManager.getLogger(DefaultExchangeDemo.class);

    public static void main(String[] args) throws Exception {
        String exchangeType = "direct";
        String queueName = "default_queue";
        logger.info("Begin: Receiving message from {} exchange with queue: {}", "default-exchange", queueName);
        Consumer.receiveMessagesWithRoutingKey("default-exchange", exchangeType, queueName, queueName);

        logger.info("Begin: Sending message to {} exchange with queue: {}", "default-exchange", queueName);
        Producer.sendMessageWithRoutingKey("default-exchange", "Hello from default exchange", exchangeType, queueName, true);

        logger.info("End: Message sent and received successfully from {}", "default-exchange");
    }
}
