package nl.hanze.stakem.command.commands;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import nl.hanze.stakem.command.Command;
import nl.hanze.stakem.command.body.CommandBody;
import nl.hanze.stakem.command.body.GenericCommandBody;
import nl.hanze.stakem.command.body.GossipBody;
import nl.hanze.stakem.net.Client;
import nl.hanze.stakem.net.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GossipCommand implements Command {
    @Override
    public boolean isValid(JsonObject json) {
        return json.has("ownPort");
    }

    @Override
    public void handleIncoming(Server server, Socket clientSocket, JsonObject json) {
        System.out.println("Received gossip command! Sending clients list...");
        List<String> clients = new ArrayList<>();
        InetSocketAddress address = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
        String hostname = address.getHostName();

        // Fix for when hosting nodes on the same network
        if (hostname.equals("192.168.1.1")) {
            hostname = clientSocket.getLocalAddress().getHostAddress();
        }

        for (Client client : server.getClients()) {
            if (client.getAddress().equals(new InetSocketAddress(hostname, json.get("ownPort").getAsInt()))) {
                continue;
            }

            clients.add(client.getAddress().getHostString() + ":" + client.getAddress().getPort());
        }

        Gson gson = new Gson();

        try (PrintWriter writer = new PrintWriter(clientSocket.getOutputStream())) {
            writer.println(gson.toJson(clients));
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleOutgoing(Server server, Client client, List<String> args) {
        CommandBody body = new GossipBody(server.getPort());
        String reply = client.sendJsonAndWaitForReply(body);
        Gson gson = new Gson();

        if (reply == null) {
            System.out.println("No reply received for gossip!");
        } else {
            Type listType = new TypeToken<ArrayList<String>>(){}.getType();
            List<String> result = gson.fromJson(reply, listType);

            System.out.println("Received the following clients:");
            for (String entry : result) {
                String[] split = entry.split(":");
                String hostname = split[0];
                int port = Integer.parseInt(split[1]);

                server.addClient(new InetSocketAddress(hostname, port));
                System.out.println(hostname + ":" + port);
            }
        }
    }
}
