package TaskB;

import com.rabbitmq.client.*;

import java.util.Scanner;

public class Client1 {
    private static final String EXCHANGE_NAME = "itblog";
    public static void main(String[] argv) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true){
            String command=scanner.nextLine();
            if (command.startsWith("set_topic")){
                command=command.substring(10);
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost("localhost");
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();

                channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

                String queueName = channel.queueDeclare().getQueue();
                System.out.println("My queue name: " + queueName);

                channel.queueBind(queueName, EXCHANGE_NAME, command);

                System.out.println(" [*] Waiting for messages");

                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                };
                channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
                });
            }
        }
    }
}
