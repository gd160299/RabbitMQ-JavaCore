package com.pj.ExchangeDemo;

import com.pj.RabbitMQ.Receive.Consumer;
import com.pj.RabbitMQ.Send.Producer;

public class DefaultExchangeDemo {
    public static void main(String[] args) throws Exception {
        String exchangeType = "direct";
        String queueName = "default_queue";
        Consumer.receiveMessagesWithRoutingKey("default-exchange", exchangeType, queueName, queueName);

        Producer.sendMessageWithRoutingKey("default-exchange", "Hello from default exchange", exchangeType, queueName, true);
    }
}
