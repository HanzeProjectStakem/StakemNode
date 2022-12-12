package nl.hanze.stakem.listeners;

import nl.hanze.stakem.event.Event;
import nl.hanze.stakem.event.Listener;
import nl.hanze.stakem.event.events.ClientRegisterEvent;
import nl.hanze.stakem.net.MessageBody;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;

public class ClientRegisterListener implements Listener {

    @Override
    public void onEvent(Event event) {
        ClientRegisterEvent registerEvent = (ClientRegisterEvent) event;
        DatagramPacket packet = registerEvent.getPacket();
        MessageBody body = registerEvent.getMessageBody();
        InetSocketAddress address = new InetSocketAddress(packet.getAddress(), body.getServerPort());

        System.out.println("Received registration request from " + packet.getAddress().getHostAddress() + ":" + body.getServerPort());
        registerEvent.getServer().addClient(address);
    }
}
