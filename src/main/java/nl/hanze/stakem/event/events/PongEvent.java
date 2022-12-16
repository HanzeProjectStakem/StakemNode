package nl.hanze.stakem.event.events;

import nl.hanze.stakem.event.MessageEvent;
import nl.hanze.stakem.net.MessageBody;
import nl.hanze.stakem.net.Server;
import nl.hanze.stakem.net.messages.PongMessage;

import java.net.DatagramPacket;

public class PongEvent extends MessageEvent<PongMessage> {
    public PongEvent(Server server, MessageBody messageBody, DatagramPacket packet, Object source) {
        super(server, messageBody, (PongMessage) messageBody.getMessage(), packet, source);
    }
}
