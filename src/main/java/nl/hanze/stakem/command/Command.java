package nl.hanze.stakem.command;

import com.google.gson.JsonObject;
import nl.hanze.stakem.net.Client;
import nl.hanze.stakem.net.Server;

import java.net.Socket;
import java.util.List;
import java.util.Map;

public interface Command {

    boolean isValid(JsonObject json);

    void handleIncoming(Server server, Socket clientSocket, JsonObject json);

    void handleOutgoing(Server server, Client client, List<String> args);

}
