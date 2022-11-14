package nl.hanze.stakem.command.commands;

import com.google.gson.JsonObject;
import nl.hanze.stakem.command.Command;
import nl.hanze.stakem.command.body.CommandBody;
import nl.hanze.stakem.command.body.EchoBody;
import nl.hanze.stakem.command.body.GenericCommandBody;
import nl.hanze.stakem.net.Client;
import nl.hanze.stakem.net.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class EchoCommand implements Command {

    @Override
    public boolean isValid(JsonObject json) {
        return json.has("message");
    }

    @Override
    public void handleOutgoing(Server server, Client client, List<String> args) {
        CommandBody body = new EchoBody(args.get(0));

        client.sendJson(body);
    }

    @Override
    public void handleIncoming(Server server, Socket clientSocket, JsonObject json) {
        String message = json.get("message").getAsString();

        System.out.println(message);
    }
}
