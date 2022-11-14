package nl.hanze.stakem.command.body;

public class RegisterBody extends CommandBody {

    private final String command = "register";
    private final int port;

    public RegisterBody(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
