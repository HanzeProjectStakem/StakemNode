package nl.hanze.stakem.event;

import nl.hanze.stakem.net.Message;
import nl.hanze.stakem.net.MessageBody;
import nl.hanze.stakem.net.Server;
import org.springframework.context.ApplicationEvent;

import java.net.DatagramPacket;

public class MessageEvent<T extends Message> extends ApplicationEvent {

    private final T message;
    private final DatagramPacket packet;
    private final MessageBody messageBody;
    private final Server server;

    public MessageEvent(Server server, MessageBody messageBody, T message, DatagramPacket packet, Object source) {
        super(source);
        this.messageBody = messageBody;
        this.message = message;
        this.packet = packet;
        this.server = server;
    }

    public T getMessage() {
        return message;
    }

    public DatagramPacket getPacket() {
        return packet;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }

    public Server getServer() {
        return server;
    }
}
