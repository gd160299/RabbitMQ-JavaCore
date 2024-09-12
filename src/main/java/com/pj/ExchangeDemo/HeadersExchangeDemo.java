package com.pj.ExchangeDemo;

import com.pj.RabbitMQ.Receive.Consumer;
import com.pj.RabbitMQ.Send.Producer;

import java.util.HashMap;
import java.util.Map;

public class HeadersExchangeDemo {
    public static void main(String[] args) throws Exception {
        String exchangeName = "headers-exchange";
        String queueName = "pdf_create";

        // Headers để bind queue với exchange
        Map<String, Object> headers = new HashMap<>();
        headers.put("x-match", "any");
        headers.put("format", "pdf");
        headers.put("type", "report");

        Consumer.receiveMessagesWithHeaders(exchangeName, queueName, headers);

        // Producer gửi thông điệp với headers
        Map<String, Object> messageHeaders = new HashMap<>();
        messageHeaders.put("format", "pdf");
        messageHeaders.put("type", "report");

        Producer.sendMessageWithHeaders(exchangeName, "This is a PDF report v2", messageHeaders, false);
    }
}
