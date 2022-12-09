package nl.hanze.stakem.event.events;

import nl.hanze.stakem.command.CommandBody;
import nl.hanze.stakem.event.Event;
import nl.hanze.stakem.net.Server;

import java.net.DatagramPacket;

public abstract class CommandEvent implements Event {

    private final CommandBody commandBody;
    private final DatagramPacket packet;
    private final Server server;

    protected CommandEvent(Server server, CommandBody commandBody, DatagramPacket packet) {
        this.commandBody = commandBody;
        this.packet = packet;
        this.server = server;
    }

    public CommandBody getCommandBody() {
        return commandBody;
    }

    public DatagramPacket getPacket() {
        return packet;
    }

    public Server getServer() {
        return server;
    }
}
