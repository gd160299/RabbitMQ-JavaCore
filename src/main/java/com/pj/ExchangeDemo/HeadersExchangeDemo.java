package com.pj.ExchangeDemo;

import com.pj.RabbitMQ.Receive.Consumer;
import com.pj.RabbitMQ.Send.Producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class HeadersExchangeDemo {
    private static final Logger logger = LogManager.getLogger(HeadersExchangeDemo.class);

    public static void main(String[] args) throws Exception {
        String exchangeName = "headers-exchange";
        String queueName = "pdf_create";

        // Headers để bind queue với exchange
        Map<String, Object> headers = new HashMap<>();
        headers.put("x-match", "any");
        headers.put("format", "pdf");
        headers.put("type", "report");

        logger.info("Begin: Receiving message with headers from {} exchange and queue: {}", exchangeName, queueName);
        Consumer.receiveMessagesWithHeaders(exchangeName, queueName, headers);

        Map<String, Object> messageHeaders = new HashMap<>();
        messageHeaders.put("format", "pdf");
        messageHeaders.put("type", "report");

        logger.info("Begin: Sending message with headers to {} exchange", exchangeName);
        Producer.sendMessageWithHeaders(exchangeName, "This is a PDF report v2", messageHeaders, false);

        logger.info("End: Message sent and received with headers successfully from {}", exchangeName);
    }
}
