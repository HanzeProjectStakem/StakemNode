package nl.hanze.stakem.event.events;

import nl.hanze.stakem.message.MessageBody;
import nl.hanze.stakem.net.Server;

import java.net.DatagramPacket;

public class GossipResultEvent extends MessageReceivedEvent {

    public GossipResultEvent(Server server, MessageBody messageBody, DatagramPacket packet) {
        super(server, messageBody, packet);
    }
}
