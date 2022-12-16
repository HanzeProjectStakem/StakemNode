package nl.hanze.stakem.event;

import nl.hanze.stakem.net.MessageBody;
import nl.hanze.stakem.net.Server;
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
        eventPublisher.publishEvent(new MessageEvent<>(server, body, body.getMessage(), packet, this));
    }
}
