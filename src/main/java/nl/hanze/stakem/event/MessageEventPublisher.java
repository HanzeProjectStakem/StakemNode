package nl.hanze.stakem.event;

import nl.hanze.stakem.event.events.*;
import nl.hanze.stakem.net.Message;
import nl.hanze.stakem.net.MessageBody;
import nl.hanze.stakem.net.Server;
import nl.hanze.stakem.net.messages.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.net.DatagramPacket;

@Component
public class MessageEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public MessageEventPublisher(@Autowired ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishEvent(Server server, MessageBody body, DatagramPacket packet) {
        Message message = body.getMessage();

        // TODO: clean this up, will get worse when new Messages are added...
        if (message instanceof RegisterMessage) {
            eventPublisher.publishEvent(new ClientRegisterEvent(server, body, packet, this));
        } else if (message instanceof GossipMessage) {
            eventPublisher.publishEvent(new GossipEvent(server, body, packet, this));
        } else if (message instanceof GossipResultMessage) {
            eventPublisher.publishEvent(new GossipResultEvent(server, body, packet, this));
        } else if (message instanceof PingMessage) {
            eventPublisher.publishEvent(new PingEvent(server, body, packet, this));
        } else if (message instanceof PongMessage) {
            eventPublisher.publishEvent(new PongEvent(server, body, packet, this));
        } else {
            throw new IllegalArgumentException("No event for message of type " + message.getClass().getName());
        }
    }
}
