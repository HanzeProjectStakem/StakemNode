package nl.hanze.stakem.event.events;

import nl.hanze.stakem.net.MessageBody;
import nl.hanze.stakem.net.Server;

import java.net.DatagramPacket;

public class GossipEvent extends MessageReceivedEvent {

    public GossipEvent(Server server, MessageBody messageBody, DatagramPacket packet) {
        super(server, messageBody, packet);
    }
}
