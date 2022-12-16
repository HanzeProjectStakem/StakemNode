package nl.hanze.stakem.listeners;

import nl.hanze.stakem.event.MessageEvent;
import nl.hanze.stakem.net.Client;
import nl.hanze.stakem.net.messages.GossipMessage;
import nl.hanze.stakem.net.messages.GossipResultMessage;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;

@Component
public class GossipListener {

    @EventListener
    public void onApplicationEvent(MessageEvent<GossipMessage> messageEvent) {
        DatagramPacket packet = messageEvent.getPacket();
        InetSocketAddress address = (InetSocketAddress) packet.getSocketAddress();
        Client client = messageEvent.getServer().getClient(address);

        System.out.println("Received gossip request from " + packet.getAddress().getHostAddress() + ":" + packet.getPort());
        GossipResultMessage resultMessage = new GossipResultMessage(messageEvent.getServer().getClientAddresses());
        client.sendMessage(resultMessage);
    }
}
