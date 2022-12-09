package nl.hanze.stakem;

import nl.hanze.stakem.command.Command;
import nl.hanze.stakem.event.EventManager;
import nl.hanze.stakem.event.events.PingEvent;
import nl.hanze.stakem.event.events.PongEvent;
import nl.hanze.stakem.listeners.PingListener;
import nl.hanze.stakem.listeners.PongListener;
import nl.hanze.stakem.net.Client;
import nl.hanze.stakem.net.Server;

import java.util.List;
import java.util.Scanner;

public class StakemNode {

    public static void main(String[] args) {
        boolean isRootNode = args.length > 0 && args[0].equalsIgnoreCase("root");
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
                String commandStr = input.split(" ")[0];
                int argsLength = input.split(" ").length - 1;

                switch (commandStr) {
                    case "list" -> {
                        System.out.println("Clients:");
                        for (Client client : server.getClients()) {
                            System.out.println(client);
                        }
                    }
                    default -> {
                        for (Client client : server.getClients()) {
                            if (argsLength > 0) {
                                List<String> cmdArgs = List.of(input.substring(commandStr.length() + 1));
                                Command command = CommandFactory.getCommand(commandStr, cmdArgs);

                                client.sendCommand(command);
                            } else {
                                Command command = CommandFactory.getCommand(commandStr);

                                client.sendCommand(command);
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
    }
}