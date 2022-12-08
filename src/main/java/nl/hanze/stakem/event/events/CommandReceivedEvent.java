package nl.hanze.stakem.event.events;

import nl.hanze.stakem.command.CommandBody;
import nl.hanze.stakem.event.Event;

import java.net.DatagramPacket;

public abstract class CommandReceivedEvent implements Event {

    private final CommandBody commandBody;
    private final DatagramPacket packet;

    public CommandReceivedEvent(CommandBody commandBody, DatagramPacket packet) {
        this.commandBody = commandBody;
        this.packet = packet;
    }

    public CommandBody getCommandBody() {
        return commandBody;
    }

    public DatagramPacket getPacket() {
        return packet;
    }
}
