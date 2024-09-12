package com.pj.ExchangeDemo;

import com.pj.RabbitMQ.Receive.Consumer;
import com.pj.RabbitMQ.Send.Producer;

public class TopicExchangeDemo {

    public static void main(String[] args) throws Exception {
        String exchangeType = "topic";
        String routingKey = "log.info.info";

        Consumer.receiveMessagesWithRoutingKey("topic-exchange", exchangeType, "topic_queue", "log.#");

        Producer.sendMessageWithRoutingKey("topic-exchange", "Info log message", exchangeType, routingKey, false);


    }
}

