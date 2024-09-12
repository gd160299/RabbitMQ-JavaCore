package com.pj.ExchangeDemo;

import com.pj.RabbitMQ.Receive.RPCServer;
import com.pj.RabbitMQ.Send.RPCClient;

public class RPCDemo {
    public static void main(String[] args) {
        Thread serverThread = new Thread(() -> {
            try {
                RPCServer.main(args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        serverThread.start();

        try {
            RPCClient rpcClient = new RPCClient();
            String response = rpcClient.call("5");
            System.out.println("Received response from server: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
