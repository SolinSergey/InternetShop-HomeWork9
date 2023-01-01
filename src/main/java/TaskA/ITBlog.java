package TaskA;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class ITBlog {

    private static final String EXCHANGE_NAME = "itblog";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            String routingKey = "php"; // #.oop
            Scanner scanner=new Scanner(System.in);
            while(true){
                String message=scanner.nextLine();
                if (message.startsWith(routingKey)){
                    message=message.substring(4);
                    channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
                    System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
                }
            }
        }
    }
}
