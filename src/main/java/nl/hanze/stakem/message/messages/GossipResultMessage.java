package nl.hanze.stakem.message.messages;

import nl.hanze.stakem.message.Message;
import nl.hanze.stakem.net.Client;

import java.util.Collection;

public class GossipResultMessage implements Message {

    private final Collection<Client> gossipResult;

    public GossipResultMessage(Collection<Client> gossipResult) {
        this.gossipResult = gossipResult;
    }

    public Collection<Client> getGossipResult() {
        return gossipResult;
    }
}
