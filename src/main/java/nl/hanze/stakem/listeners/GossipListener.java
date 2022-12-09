package nl.hanze.stakem.listeners;

import nl.hanze.stakem.event.Event;
import nl.hanze.stakem.event.Listener;
import nl.hanze.stakem.event.events.GossipEvent;
import nl.hanze.stakem.message.messages.GossipResultMessage;
import nl.hanze.stakem.net.Client;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;

public class GossipListener implements Listener {

    @Override
    public void onEvent(Event event) {
        GossipEvent gossipEvent = (GossipEvent) event;
        DatagramPacket packet = gossipEvent.getPacket();
        InetSocketAddress address = (InetSocketAddress) packet.getSocketAddress();
        Client client = gossipEvent.getServer().getClient(address);

        System.out.println("Received gossip request from " + packet.getAddress().getHostAddress() + ":" + packet.getPort());
        GossipResultMessage resultMessage = new GossipResultMessage(gossipEvent.getServer().getClients());
        client.sendMessage(resultMessage);
    }
}
