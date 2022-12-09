package nl.hanze.stakem.event.events;

import nl.hanze.stakem.message.MessageBody;
import nl.hanze.stakem.net.Server;

import java.net.DatagramPacket;

public class ClientRegisterEvent extends MessageReceivedEvent {

    public ClientRegisterEvent(Server server, MessageBody messageBody, DatagramPacket packet) {
        super(server, messageBody, packet);
    }
}
