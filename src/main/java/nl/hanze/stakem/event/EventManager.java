package nl.hanze.stakem.event;

import java.util.*;

public class EventManager {

    private final Map<Class<? extends Event>, List<Listener>> listeners = new HashMap<>();

    public void registerListener(Listener listener, Class<? extends Event> event) {
        if (!listeners.containsKey(event)) {
            listeners.put(event, new ArrayList<>(Collections.singletonList(listener)));
        } else {
            listeners.get(event).add(listener);
        }
    }

    public void notify(Event event) {
        if (!listeners.containsKey(event.getClass())) {
            throw new IllegalArgumentException("Unknown event");
        }

        for (Listener listener : listeners.get(event.getClass())) {
            listener.onEvent(event);
        }
    }
}
