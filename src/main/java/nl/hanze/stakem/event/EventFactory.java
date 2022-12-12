package nl.hanze.stakem.event;

import nl.hanze.stakem.event.events.*;
import nl.hanze.stakem.net.Message;
import nl.hanze.stakem.net.MessageBody;
import nl.hanze.stakem.net.Server;
import nl.hanze.stakem.net.messages.*;

import java.lang.reflect.InvocationTargetException;
import java.net.DatagramPacket;
import java.util.Map;

public class EventFactory {

    private static final Map<Class<? extends Message>, Class<? extends Event>> messageEvents = Map.of(
            PingMessage.class, PingEvent.class,
            PongMessage.class, PongEvent.class,
            RegisterMessage.class, ClientRegisterEvent.class,
            GossipMessage.class, GossipEvent.class,
            GossipResultMessage.class, GossipResultEvent.class
    );

    public static Event getEvent(Server server, MessageBody messageBody, DatagramPacket packet) {
        Message message = messageBody.getMessage();
        Class<? extends Event> eventClass = messageEvents.get(message.getClass());

        if (eventClass == null) {
            throw new IllegalArgumentException("No event class found for message " + message.getClass().getName());
        }

        try {
            return eventClass.getConstructor(Server.class, MessageBody.class, DatagramPacket.class)
                    .newInstance(server, messageBody, packet);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Could not instantiate event class " + eventClass.getName(), e);
        }
    }
}
