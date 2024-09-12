package com.pj.ExchangeDemo;

import com.pj.RabbitMQ.Receive.Consumer;
import com.pj.RabbitMQ.Send.Producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TopicExchangeDemo {
    private static final Logger logger = LogManager.getLogger(TopicExchangeDemo.class);

    public static void main(String[] args) throws Exception {
        String exchangeType = "topic";
        String routingKey = "log.info.info";

        logger.info("Begin: Receiving message from {} exchange with routing key: {}", "topic-exchange", "log.#");
        Consumer.receiveMessagesWithRoutingKey("topic-exchange", exchangeType, "topic_queue", "log.#");

        logger.info("Begin: Sending message to {} exchange with routing key: {}", "topic-exchange", routingKey);
        Producer.sendMessageWithRoutingKey("topic-exchange", "Info log message", exchangeType, routingKey, false);

        logger.info("End: Message sent and received successfully from {}", "topic-exchange");
    }
}

