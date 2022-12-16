package nl.hanze.stakem.event.events;

import nl.hanze.stakem.event.MessageEvent;
import nl.hanze.stakem.net.MessageBody;
import nl.hanze.stakem.net.Server;
import nl.hanze.stakem.net.messages.GossipMessage;
import nl.hanze.stakem.net.messages.RegisterMessage;

import java.net.DatagramPacket;

public class GossipEvent extends MessageEvent<GossipMessage> {
    public GossipEvent(Server server, MessageBody messageBody, DatagramPacket packet, Object source) {
        super(server, messageBody, (GossipMessage) messageBody.getMessage(), packet, source);
    }
}
