package com.pj.ExchangeDemo;

import com.pj.RabbitMQ.Receive.Consumer;
import com.pj.RabbitMQ.Send.Producer;

public class FanoutExchangeDemo {

    public static void main(String[] args) throws Exception {
        String exchangeType = "fanout";

        Consumer.receiveMessagesWithRoutingKey("fanout-exchange", exchangeType, "fanout_queue1", "");
        Consumer.receiveMessagesWithRoutingKey("fanout-exchange", exchangeType, "fanout_queue2", "");
        Consumer.receiveMessagesWithRoutingKey("fanout-exchange", exchangeType, "fanout_queue3", "");

        Producer.sendMessageWithRoutingKey("fanout-exchange", "Hello from fanout exchange", exchangeType, "", false);


    }
}

