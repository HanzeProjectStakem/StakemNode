package nl.hanze.stakem.listeners;

import nl.hanze.stakem.event.MessageEvent;
import nl.hanze.stakem.net.Client;
import nl.hanze.stakem.net.messages.PingMessage;
import nl.hanze.stakem.net.messages.PongMessage;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;

@Component
public class PingListener {

    @EventListener
    public void onEvent(MessageEvent<PingMessage> messageEvent) {
        DatagramPacket packet = messageEvent.getPacket();
        InetSocketAddress address = (InetSocketAddress) packet.getSocketAddress();
        Client client = messageEvent.getServer().getClient(address);

        System.out.println("Received ping from " + packet.getAddress().getHostAddress() + ":" + packet.getPort());
        client.sendMessage(new PongMessage());
    }
}
