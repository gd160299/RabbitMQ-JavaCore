package com.pj.ExchangeDemo;

import com.pj.RabbitMQ.Receive.RPCServer;
import com.pj.RabbitMQ.Send.RPCClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RPCDemo {
    private static final Logger logger = LogManager.getLogger(RPCDemo.class);

    public static void main(String[] args) {
        Thread serverThread = new Thread(() -> {
            try {
                logger.info("Begin: Starting RPC server");
                RPCServer.main(args);
                logger.info("End: RPC server started successfully");
            } catch (Exception e) {
                logger.error("Error starting RPC server: {}", e.getMessage(), e);
            }
        });

        serverThread.start();

        try (RPCClient rpcClient = new RPCClient()) {
            String msg = "30";
            logger.info("Begin: Sending RPC request to server with message: {}", msg);
            String response = rpcClient.call(msg);
            logger.info("End: Received response from server: {}", response);
        } catch (Exception e) {
            logger.error("Error occurred during RPC communication: {}", e.getMessage(), e);
        }
    }
}
