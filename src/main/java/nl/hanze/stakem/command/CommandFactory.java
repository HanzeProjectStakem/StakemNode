package nl.hanze.stakem.command;

import nl.hanze.stakem.command.commands.EchoCommand;
import nl.hanze.stakem.command.commands.GossipCommand;
import nl.hanze.stakem.command.commands.PingCommand;
import nl.hanze.stakem.command.commands.RegisterCommand;

public class CommandFactory {

    private CommandFactory() {}

    public static Command getCommand(String command) {
        return switch (command) {
            case "ping" -> new PingCommand();
            case "register" -> new RegisterCommand();
            case "gossip" -> new GossipCommand();
            case "echo" -> new EchoCommand();
            default -> null;
        };
    }

}
