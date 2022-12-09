package nl.hanze.stakem.command.commands;

import nl.hanze.stakem.command.Command;

public class RegisterCommand implements Command {

    private final int port;

    public RegisterCommand(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
