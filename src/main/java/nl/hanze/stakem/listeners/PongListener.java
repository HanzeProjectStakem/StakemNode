package nl.hanze.stakem.listeners;

import nl.hanze.stakem.event.Event;
import nl.hanze.stakem.event.Listener;
import nl.hanze.stakem.event.events.PingEvent;

import java.net.DatagramPacket;

public class PongListener implements Listener {

    @Override
    public void onEvent(Event event) {
        PingEvent pingEvent = (PingEvent) event;
        DatagramPacket packet = pingEvent.getPacket();

        System.out.println("Received pong from " + packet.getAddress().getHostAddress() + ":" + packet.getPort());
    }
}
