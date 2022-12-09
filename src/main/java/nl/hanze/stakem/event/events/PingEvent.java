package nl.hanze.stakem.event.events;

import nl.hanze.stakem.command.CommandBody;
import nl.hanze.stakem.net.Server;

import java.net.DatagramPacket;

public class PingEvent extends CommandEvent {

    public PingEvent(Server server, CommandBody commandBody, DatagramPacket packet) {
        super(server, commandBody, packet);
    }
}
