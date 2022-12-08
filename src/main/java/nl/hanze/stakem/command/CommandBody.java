package nl.hanze.stakem.command;

import nl.hanze.stakem.Constants;

import java.time.Instant;

public class CommandBody {

    private String version;

    private Long timestamp;

    private Command command;

    public CommandBody(Command command) {
        this.version = Constants.VERSION;
        this.command = command;
        this.timestamp = Instant.now().toEpochMilli();
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

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}
