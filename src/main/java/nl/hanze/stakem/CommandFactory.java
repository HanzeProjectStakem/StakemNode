package nl.hanze.stakem;

import nl.hanze.stakem.command.Command;
import nl.hanze.stakem.command.commands.PingCommand;

import java.util.List;

public class CommandFactory {
    public static Command getCommand(String command) {
        return getCommand(command, null);
    }

    public static Command getCommand(String command, List<String> args) {
        return switch (command) {
            case "ping" -> new PingCommand();
            default -> null;
        };
    }
}
