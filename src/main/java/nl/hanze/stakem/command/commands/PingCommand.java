package nl.hanze.stakem.command.commands;

import com.google.gson.JsonObject;
import nl.hanze.stakem.command.Command;
import nl.hanze.stakem.command.body.CommandBody;
import nl.hanze.stakem.command.body.GenericCommandBody;
import nl.hanze.stakem.net.Client;
import nl.hanze.stakem.net.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class PingCommand implements Command {

    @Override
    public boolean isValid(JsonObject json) {
        return true;
    }

    @Override
    public void handleOutgoing(Server server, Client client, List<String> args) {
        CommandBody body = new GenericCommandBody("ping");
        String reply = client.sendJsonAndWaitForReply(body);

        if (reply == null) {
            System.out.println("No reply received. Removing client...");
            server.removeClient(client.getAddress());
        } else {
            System.out.println("Received pong!");
        }
    }

    @Override
    public void handleIncoming(Server server, Socket clientSocket, JsonObject json) {
        System.out.println("Received ping! Sending pong back...");
        CommandBody body = new GenericCommandBody("pong");
        String jsonString = body.toJsonString();

        try (PrintWriter writer = new PrintWriter(clientSocket.getOutputStream())) {
            writer.println(jsonString);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
