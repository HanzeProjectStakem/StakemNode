package nl.hanze.stakem.net;

import java.time.Instant;

public class MessageBody {

    private String version;

    private int serverPort;

    private Long timestamp;

    private Message message;

    public MessageBody() {
        this.version = "";
        this.serverPort = -1;
        this.timestamp = Instant.now().toEpochMilli();
        this.message = null;
    }

    public MessageBody(String version, Message message, Server server) {
        this.version = version;
        this.message = message;
        this.timestamp = Instant.now().toEpochMilli();
        this.serverPort = server.getPort();
    }

    public MessageBody(String version, int serverPort, Long timestamp, Message message) {
        this.version = version;
        this.serverPort = serverPort;
        this.timestamp = timestamp;
        this.message = message;
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
