package nl.hanze.stakem.event;

import nl.hanze.stakem.command.Command;
import nl.hanze.stakem.command.CommandBody;
import nl.hanze.stakem.command.commands.PingCommand;
import nl.hanze.stakem.event.events.PingEvent;

import java.lang.reflect.InvocationTargetException;
import java.net.DatagramPacket;
import java.util.Map;

public class EventFactory {

    private static final Map<Class<? extends Command>, Class<? extends Event>> commandEvents = Map.of(
            PingCommand.class, PingEvent.class
    );

    public static Event getEvent(CommandBody commandBody, DatagramPacket packet) {
        Command command = commandBody.getCommand();
        Class<? extends Event> eventClass = commandEvents.get(command.getClass());

        if (eventClass == null) {
            throw new IllegalArgumentException("No event class found for command " + command.getClass().getName());
        }

        try {
            return eventClass.getConstructor(CommandBody.class, DatagramPacket.class).newInstance(commandBody, packet);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException("Could not instantiate event class " + eventClass.getName(), e);
        }
    }
}
