package nl.hanze.stakem.listeners;

import nl.hanze.stakem.event.MessageEvent;
import nl.hanze.stakem.net.messages.PongMessage;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.DatagramPacket;

@Component
public class PongListener {

    @EventListener
    public void onEvent(MessageEvent<PongMessage> messageEvent) {
        DatagramPacket packet = messageEvent.getPacket();

        System.out.println("Received pong from " + packet.getAddress().getHostAddress() + ":" + packet.getPort());
    }
}
