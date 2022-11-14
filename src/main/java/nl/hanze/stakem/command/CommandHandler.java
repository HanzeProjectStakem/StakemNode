package nl.hanze.stakem.command;

import com.google.gson.*;
import nl.hanze.stakem.exceptions.InvalidCommandException;
import nl.hanze.stakem.net.Server;

import java.io.*;
import java.net.Socket;

public class CommandHandler {

    private final Socket socket;
    private final Server server;

    public CommandHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public void handle() throws InvalidCommandException, IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String commandStr = in.readLine();

            if (!isJsonValid(commandStr)) {
                System.out.println(commandStr);
                throw new InvalidCommandException("Invalid JSON");
            }

            JsonObject commandJson = JsonParser.parseString(commandStr).getAsJsonObject();

            if (!commandJson.has("command")) {
                System.out.println(commandStr);
                throw new InvalidCommandException("Command is missing");
            }

            Command command = CommandFactory.getCommand(commandJson.get("command").getAsString());

            if (command == null) {
                System.out.println(commandStr);
                throw new InvalidCommandException("Invalid command");
            }

            if (command.isValid(commandJson)) {
                command.handleIncoming(this.server, socket, commandJson);
            } else {
                System.out.println(commandStr);
                throw new InvalidCommandException("Invalid command args");
            }
        } finally {
            //socket.close();
        }
    }

    private boolean isJsonValid(String json) {
        if (json == null) {
            return false;
        }

        try {
            JsonElement elem = JsonParser.parseString(json);

            return elem != null && elem.isJsonObject() && !elem.isJsonNull();
        } catch (JsonParseException | IllegalStateException e) {
            return false;
        }
    }
}
