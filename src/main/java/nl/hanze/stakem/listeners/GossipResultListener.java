package nl.hanze.stakem.listeners;

import nl.hanze.stakem.event.Event;
import nl.hanze.stakem.event.Listener;
import nl.hanze.stakem.event.events.GossipResultEvent;
import nl.hanze.stakem.message.MessageBody;
import nl.hanze.stakem.message.messages.GossipResultMessage;
import nl.hanze.stakem.net.Client;

import java.net.DatagramPacket;

public class GossipResultListener implements Listener {

    @Override
    public void onEvent(Event event) {
        GossipResultEvent gossipResultEvent = (GossipResultEvent) event;
        MessageBody body = gossipResultEvent.getMessageBody();
        GossipResultMessage resultMessage = (GossipResultMessage) body.getMessage();
        DatagramPacket packet = gossipResultEvent.getPacket();

        System.out.println("Received gossip result from " + packet.getAddress().getHostAddress() + ":" + packet.getPort());
        for (Client client : resultMessage.getGossipResult()) {
            System.out.println("Received client: " + client.getAddress().getHostString() + ":" + client.getAddress().getPort());
            gossipResultEvent.getServer().addClient(client.getAddress());
        }
    }
}
