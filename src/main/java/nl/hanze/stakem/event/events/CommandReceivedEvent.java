package nl.hanze.stakem.event.events;

import nl.hanze.stakem.command.Command;
import nl.hanze.stakem.event.Event;

public class CommandReceivedEvent implements Event {

    private final Command command;

    public CommandReceivedEvent(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
