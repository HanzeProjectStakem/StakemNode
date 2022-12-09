package nl.hanze.stakem.listeners;

import nl.hanze.stakem.command.Command;
import nl.hanze.stakem.command.commands.PongCommand;
import nl.hanze.stakem.event.Event;
import nl.hanze.stakem.event.Listener;
import nl.hanze.stakem.event.events.PingEvent;
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
        Command command = new PongCommand();
        client.sendCommand(command);
    }
}
