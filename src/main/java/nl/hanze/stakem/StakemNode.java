package nl.hanze.stakem;

import nl.hanze.stakem.event.EventManager;
import nl.hanze.stakem.event.events.*;
import nl.hanze.stakem.listeners.*;
import nl.hanze.stakem.net.Message;
import nl.hanze.stakem.net.Client;
import nl.hanze.stakem.net.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
class StakemNodeApplication {

    @Autowired
    private ApplicationArguments args;

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        boolean isRootNode = args.getSourceArgs().length > 0 && args.getSourceArgs()[0].equals("root");
        Server server = new Server(Constants.DEFAULT_PORT, isRootNode);

        if (isRootNode) {
            System.out.println("Starting as root node...");
        } else {
            System.out.println("Starting as normal node...");
        }

        registerListeners();
        server.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String input = scanner.nextLine();
                String messageStr = input.split(" ")[0];
                int argsLength = input.split(" ").length - 1;

                switch (messageStr) {
                    case "list" -> {
                        System.out.println("Clients:");
                        for (Client client : server.getClients()) {
                            System.out.println(client);
                        }
                    }
                    default -> {
                        for (Client client : server.getClients()) {
                            if (argsLength > 0) {
                                List<String> messageArgs = List.of(input.substring(messageStr.length() + 1));
                                Message message = MessageFactory.getMessage(messageStr, messageArgs);

                                client.sendMessage(message);
                            } else {
                                Message message = MessageFactory.getMessage(messageStr);

                                client.sendMessage(message);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void registerListeners() {
        EventManager manager = EventManager.getInstance();

        manager.registerListener(new PingListener(), PingEvent.class);
        manager.registerListener(new PongListener(), PongEvent.class);
        manager.registerListener(new ClientRegisterListener(), ClientRegisterEvent.class);
        manager.registerListener(new GossipListener(), GossipEvent.class);
        manager.registerListener(new GossipResultListener(), GossipResultEvent.class);
    }
}

public class StakemNode {
    public static void main(String[] args) {
        SpringApplication.run(StakemNodeApplication.class, args);
    }
}