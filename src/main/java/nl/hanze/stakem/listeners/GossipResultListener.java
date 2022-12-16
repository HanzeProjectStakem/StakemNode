package nl.hanze.stakem.listeners;

import nl.hanze.stakem.event.MessageEvent;
import nl.hanze.stakem.net.messages.GossipResultMessage;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;

@Component
public class GossipResultListener {

    @EventListener
    public void onApplicationEvent(MessageEvent<GossipResultMessage> messageEvent) {
        GossipResultMessage resultMessage = messageEvent.getMessage();
        DatagramPacket packet = messageEvent.getPacket();

        System.out.println("Received gossip result from " + packet.getAddress().getHostAddress() + ":" + packet.getPort());
        for (InetSocketAddress clientAddress : resultMessage.getGossipResult()) {
            System.out.println("Received client: " + clientAddress);
            messageEvent.getServer().addClient(clientAddress);
        }
    }
}
