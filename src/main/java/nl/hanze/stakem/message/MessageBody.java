package nl.hanze.stakem.message;

import nl.hanze.stakem.Constants;
import nl.hanze.stakem.net.Server;

import java.time.Instant;

public class MessageBody {

    private String version;

    private int serverPort;

    private Long timestamp;

    private Message message;

    public MessageBody(Message message, Server server) {
        this.version = Constants.VERSION;
        this.message = message;
        this.timestamp = Instant.now().toEpochMilli();
        this.serverPort = server.getPort();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
}
