package nl.hanze.stakem.command.body;

public class EchoBody extends CommandBody {

    private final String command = "echo";
    private final String message;

    public EchoBody(String message) {
        this.message = message;
    }

    public String getCommand() {
        return command;
    }
}
