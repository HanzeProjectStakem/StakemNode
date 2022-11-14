package nl.hanze.stakem.command.body;

public class GossipBody extends CommandBody {

    private final String command = "gossip";
    private final int ownPort;

    public GossipBody(int ownPort) {
        this.ownPort = ownPort;
    }

    public int getOwnPort() {
        return ownPort;
    }
}
