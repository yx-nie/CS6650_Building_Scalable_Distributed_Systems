package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Emitlog {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("localhost");
        try (Connection connection = cf.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            String severity = getSeverity(args);
            String message = getMesssage(args);

            channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + severity + "'" + message + "'");

        }
    }

    public static String getSeverity (String[] args) {
        if (args.length <1) {
            return "info";
        }
        return args[0];
    }
    public static String getMesssage (String[] args) {
        if (args.length < 2) {
            return "Hello World";
        }
        return args[1];
    }

    public static String joinStrings (String[] args, String delimiter, int startIndex) {
        int len = args.length;
        if (len == 0) {
            return "";
        }
        if (len < startIndex) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i=startIndex+1; i<len; i++) {
            sb.append(delimiter).append(args[i]);
        }
        return sb.toString();
    }



}
