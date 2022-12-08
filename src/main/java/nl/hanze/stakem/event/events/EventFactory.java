package nl.hanze.stakem.event.events;

import nl.hanze.stakem.command.CommandBody;
import nl.hanze.stakem.event.Event;

import java.net.DatagramPacket;
import java.util.List;

public class EventFactory {

    private final List<Class<? extends Event>> commandEvents = List.of();

    public static CommandReceivedEvent getEvent(CommandBody commandBody, DatagramPacket packet) {
        return null;
    }
}
