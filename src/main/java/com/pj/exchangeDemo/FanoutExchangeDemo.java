package com.pj.exchangeDemo;

import com.pj.rabbitMQ.receive.Consumer;
import com.pj.rabbitMQ.send.Producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FanoutExchangeDemo {

    private static final Logger logger = LogManager.getLogger(FanoutExchangeDemo.class);

    public static void main(String[] args) throws Exception {
        String exchangeType = "fanout";

        logger.info("Begin: Receiving message from {} exchange with queues: fanout_queue1, fanout_queue2, fanout_queue3", "fanout-exchange");
        Consumer.receiveMessagesWithRoutingKey("fanout-exchange", exchangeType, "fanout_queue1", "");
        Consumer.receiveMessagesWithRoutingKey("fanout-exchange", exchangeType, "fanout_queue2", "");
        Consumer.receiveMessagesWithRoutingKey("fanout-exchange", exchangeType, "fanout_queue3", "");

        logger.info("Begin: Sending message to {} exchange", "fanout-exchange");
        Producer.sendMessageWithRoutingKey("fanout-exchange", "Hello from fanout exchange", exchangeType, "", false);

        logger.info("End: Message sent and received successfully from {}", "fanout-exchange");
    }
}

