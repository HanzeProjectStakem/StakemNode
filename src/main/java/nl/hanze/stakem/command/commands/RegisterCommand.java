package nl.hanze.stakem.command.commands;

import com.google.gson.JsonObject;
import nl.hanze.stakem.command.Command;
import nl.hanze.stakem.command.body.CommandBody;
import nl.hanze.stakem.command.body.GenericCommandBody;
import nl.hanze.stakem.command.body.RegisterBody;
import nl.hanze.stakem.net.Client;
import nl.hanze.stakem.net.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class RegisterCommand implements Command {

    @Override
    public boolean isValid(JsonObject json) {
        return json.has("port");
    }

    @Override
    public void handleOutgoing(Server server, Client client, List<String> args) {
        System.out.println("Sending register command...");
        CommandBody body = new RegisterBody(server.getPort());

        client.sendJson(body);
    }

    @Override
    public void handleIncoming(Server server, Socket clientSocket, JsonObject json) {
        System.out.println("Received register command! Registering client...");
        InetSocketAddress address = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
        String hostname = address.getHostName();

        // Fix for when hosting nodes on the same network
        if (hostname.equals("192.168.1.1")) {
            hostname = clientSocket.getLocalAddress().getHostAddress();
        }

        server.addClient(new InetSocketAddress(hostname, json.get("port").getAsInt()));
    }
}
