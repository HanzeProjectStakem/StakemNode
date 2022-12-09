package nl.hanze.stakem.listeners;

import nl.hanze.stakem.event.Event;
import nl.hanze.stakem.event.Listener;
import nl.hanze.stakem.event.events.PingEvent;
import nl.hanze.stakem.message.Message;
import nl.hanze.stakem.message.messages.PongMessage;
import nl.hanze.stakem.net.Client;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;

public class PingListener implements Listener {

    @Override
    public void onEvent(Event event) {
        PingEvent pingEvent = (PingEvent) event;
        DatagramPacket packet = pingEvent.getPacket();
        InetSocketAddress address = (InetSocketAddress) packet.getSocketAddress();
        Client client = pingEvent.getServer().getClient(address);

        System.out.println("Received ping from " + packet.getAddress().getHostAddress() + ":" + packet.getPort());
        Message message = new PongMessage();
        client.sendMessage(message);
    }
}
