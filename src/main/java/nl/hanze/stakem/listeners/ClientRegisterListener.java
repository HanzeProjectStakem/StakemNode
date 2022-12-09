package nl.hanze.stakem.listeners;

import nl.hanze.stakem.command.commands.RegisterCommand;
import nl.hanze.stakem.event.Event;
import nl.hanze.stakem.event.Listener;
import nl.hanze.stakem.event.events.ClientRegisterEvent;

import java.net.DatagramPacket;
import java.net.InetSocketAddress;

public class ClientRegisterListener implements Listener {

    @Override
    public void onEvent(Event event) {
        ClientRegisterEvent registerEvent = (ClientRegisterEvent) event;
        DatagramPacket packet = registerEvent.getPacket();
        RegisterCommand command = (RegisterCommand) registerEvent.getCommandBody().getCommand();
        InetSocketAddress address = new InetSocketAddress(packet.getAddress(), command.getPort());

        System.out.println("Received registration request from " + packet.getAddress().getHostAddress() + ":" + packet.getPort());
        registerEvent.getServer().addClient(address);
    }
}
