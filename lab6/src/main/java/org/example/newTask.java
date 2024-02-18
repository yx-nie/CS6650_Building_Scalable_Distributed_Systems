package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;

public class newTask {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("localhost");

        try (Connection conn = cf.newConnection();
             Channel channel = conn.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            String message = String.join(" ", args);
            channel.basicPublish("","hello", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
