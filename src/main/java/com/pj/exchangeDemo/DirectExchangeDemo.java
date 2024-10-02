package com.pj.exchangeDemo;

import com.pj.rabbitMQ.receive.Consumer;
import com.pj.rabbitMQ.send.Producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DirectExchangeDemo {

    private static final Logger logger = LogManager.getLogger(DirectExchangeDemo.class);

    public static void main(String[] args) throws Exception {
        String exchangeType = "direct";
        String routingKey = "info";

        logger.info("Begin: Receiving message from {} exchange with routing key: {}", "direct-exchange", routingKey);
        Consumer.receiveMessagesWithRoutingKey("direct-exchange", exchangeType, "direct_queue", routingKey);

        logger.info("Begin: Sending message to {} exchange with routing key: {}", "direct-exchange", routingKey);
        Producer.sendMessageWithRoutingKey("direct-exchange", "Hello from direct exchange", exchangeType, routingKey, false);

        logger.info("End: Message sent and received successfully from {}", "direct-exchange");
    }
}
