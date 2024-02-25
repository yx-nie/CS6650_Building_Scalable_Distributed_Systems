package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class ReveiveLogs {
    private static final String EXCHANGE_NAME = "logs";

    public static void main (String[] args) throws Exception {
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("localhost");

        Connection connection = cf.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String queueName = channel.queueDeclare().getQueue();

        if (args.length > 1) {
            System.err.println("Usage: ReceiveLogsDirect info warning error");
            System.exit(1);
        }

        for (String severity: args) {
            channel.queueBind(queueName, EXCHANGE_NAME, severity);
        }
        System.out.println("[*] waiting for messages. to exit press CTRL+C");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String (delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }
}
