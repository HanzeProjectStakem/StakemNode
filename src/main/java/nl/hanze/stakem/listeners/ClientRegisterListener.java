package nl.hanze.stakem.listeners;

import nl.hanze.stakem.event.MessageEvent;
import nl.hanze.stakem.net.MessageBody;
import nl.hanze.stakem.net.messages.RegisterMessage;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;

@Component
public class ClientRegisterListener {
    @EventListener
    public void onApplicationEvent(MessageEvent<RegisterMessage> event) {
        DatagramPacket packet = event.getPacket();
        MessageBody body = event.getMessageBody();
        InetSocketAddress address = new InetSocketAddress(event.getPacket().getAddress(), body.getServerPort());

        System.out.println("Received registration request from " + packet.getAddress().getHostAddress() + ":" + body.getServerPort());
        event.getServer().addClient(address);
    }
}
