package nl.hanze.stakem.event.events;

import nl.hanze.stakem.event.Event;
import nl.hanze.stakem.message.MessageBody;
import nl.hanze.stakem.net.Server;

import java.net.DatagramPacket;

public abstract class MessageReceivedEvent implements Event {

    private final MessageBody messageBody;
    private final DatagramPacket packet;
    private final Server server;

    protected MessageReceivedEvent(Server server, MessageBody messageBody, DatagramPacket packet) {
        this.messageBody = messageBody;
        this.packet = packet;
        this.server = server;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }

    public DatagramPacket getPacket() {
        return packet;
    }

    public Server getServer() {
        return server;
    }
}
