package nl.hanze.stakem.listeners;

import nl.hanze.stakem.event.Event;
import nl.hanze.stakem.event.Listener;
import nl.hanze.stakem.event.events.PongEvent;

import java.net.DatagramPacket;

public class PongListener implements Listener {

    @Override
    public void onEvent(Event event) {
        PongEvent pongEvent = (PongEvent) event;
        DatagramPacket packet = pongEvent.getPacket();

        System.out.println("Received pong from " + packet.getAddress().getHostAddress() + ":" + packet.getPort());
    }
}
