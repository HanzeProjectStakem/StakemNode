package nl.hanze.stakem.net.messages;

import nl.hanze.stakem.net.Message;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class GossipResultMessage implements Message {

    private List<InetSocketAddress> gossipResult;

    public GossipResultMessage() {
        gossipResult = new ArrayList<>();
    }

    public GossipResultMessage(List<InetSocketAddress> clientAddresses) {
        this.gossipResult = clientAddresses;
    }

    public List<InetSocketAddress> getGossipResult() {
        return gossipResult;
    }

    public void setGossipResult(List<InetSocketAddress> gossipResult) {
        this.gossipResult = gossipResult;
    }
}
