package nl.hanze.stakem.event;

import java.util.*;

public class EventManager {

    private final Map<Class<? extends Event>, List<Listener>> listeners = new HashMap<>();
    private static EventManager instance;

    private EventManager() {}

    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }

        return instance;
    }

    public void registerListener(Listener listener, Class<? extends Event> event) {
        if (!listeners.containsKey(event)) {
            listeners.put(event, new ArrayList<>(Collections.singletonList(listener)));
        } else {
            listeners.get(event).add(listener);
        }
    }

    public void callEvent(Event event) {
        if (!listeners.containsKey(event.getClass())) {
            throw new IllegalArgumentException("Unknown event");
        }

        for (Listener listener : listeners.get(event.getClass())) {
            listener.onEvent(event);
        }
    }
}
