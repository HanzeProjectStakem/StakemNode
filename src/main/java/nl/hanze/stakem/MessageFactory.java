package nl.hanze.stakem;

import nl.hanze.stakem.message.Message;
import nl.hanze.stakem.message.messages.GossipMessage;
import nl.hanze.stakem.message.messages.PingMessage;
import nl.hanze.stakem.message.messages.PongMessage;
import nl.hanze.stakem.message.messages.RegisterMessage;

import java.util.List;

public class MessageFactory {
    private MessageFactory() {
    }

    public static Message getMessage(String message) {
        return getMessage(message, null);
    }

    public static Message getMessage(String message, List<String> args) {
        return switch (message) {
            case "ping" -> new PingMessage();
            case "pong" -> new PongMessage();
            case "gossip" -> new GossipMessage();
            case "register" -> new RegisterMessage();
            default -> null;
        };
    }
}
