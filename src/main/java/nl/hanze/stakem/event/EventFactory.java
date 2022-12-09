package nl.hanze.stakem.event;

import nl.hanze.stakem.event.events.PingEvent;
import nl.hanze.stakem.message.Message;
import nl.hanze.stakem.message.MessageBody;
import nl.hanze.stakem.message.messages.PingMessage;

import java.lang.reflect.InvocationTargetException;
import java.net.DatagramPacket;
import java.util.Map;

public class EventFactory {

    private static final Map<Class<? extends Message>, Class<? extends Event>> messageEvents = Map.of(
            PingMessage.class, PingEvent.class
    );

    public static Event getEvent(MessageBody messageBody, DatagramPacket packet) {
        Message message = messageBody.getMessage();
        Class<? extends Event> eventClass = messageEvents.get(message.getClass());

        if (eventClass == null) {
            throw new IllegalArgumentException("No event class found for message " + message.getClass().getName());
        }

        try {
            return eventClass.getConstructor(MessageBody.class, DatagramPacket.class).newInstance(messageBody, packet);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException("Could not instantiate event class " + eventClass.getName(), e);
        }
    }
}
