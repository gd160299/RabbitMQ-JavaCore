package com.pj.ExchangeDemo;

import com.pj.RabbitMQ.Receive.Consumer;
import com.pj.RabbitMQ.Send.Producer;

public class DirectExchangeDemo {

    public static void main(String[] args) throws Exception {
        String exchangeType = "direct";
        String routingKey = "info";

        Consumer.receiveMessagesWithRoutingKey("direct-exchange", exchangeType, "direct_queue", routingKey);

        Producer.sendMessageWithRoutingKey("direct-exchange", "Hello from direct exchange", exchangeType, routingKey, false);


    }
}
