package nl.hanze.stakem.event.events;

import nl.hanze.stakem.net.MessageBody;
import nl.hanze.stakem.net.Server;

import java.net.DatagramPacket;

public class PongEvent extends MessageReceivedEvent {

    public PongEvent(Server server, MessageBody messageBody, DatagramPacket packet) {
        super(server, messageBody, packet);
    }
}
