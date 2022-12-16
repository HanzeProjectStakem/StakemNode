package nl.hanze.stakem;

import nl.hanze.stakem.config.NodeConfig;
import nl.hanze.stakem.net.Client;
import nl.hanze.stakem.net.Message;
import nl.hanze.stakem.net.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
@ConfigurationPropertiesScan("nl.hanze.stakem.config")
class StakemNodeApplication {

    @Autowired
    private NodeConfig nodeConfig;

    @Autowired
    private ApplicationArguments args;

    @Autowired
    @Lazy
    private Server server;

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        if (server.isRootNode()) {
            System.out.println("Starting as root node...");
        } else {
            System.out.println("Starting as normal node...");
        }

        server.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String input = scanner.nextLine();
                String messageStr = input.split(" ")[0];
                int argsLength = input.split(" ").length - 1;

                for (Client client : server.getClients()) {
                    if (messageStr.equals("list")) {
                        System.out.println(client);
                    } else if (argsLength > 0) {
                        List<String> messageArgs = List.of(input.substring(messageStr.length() + 1));
                        Message message = MessageFactory.getMessage(messageStr, messageArgs);

                        client.sendMessage(message);
                    } else {
                        Message message = MessageFactory.getMessage(messageStr);

                        client.sendMessage(message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Bean
    public Server server() {
        boolean isRootNode = args.getSourceArgs().length > 0 && args.getSourceArgs()[0].equals("root");

        return new Server(nodeConfig.getDefaultNodePort(), isRootNode);
    }
}

public class StakemNode {
    public static void main(String[] args) {
        SpringApplication.run(StakemNodeApplication.class, args);
    }
}