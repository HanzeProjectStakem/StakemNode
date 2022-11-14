package nl.hanze.stakem.net;

import nl.hanze.stakem.command.Command;
import nl.hanze.stakem.command.body.CommandBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {

    private final InetSocketAddress address;
    private final Server server;

    public Client(Server server, InetSocketAddress address) {
        this.server = server;
        this.address = address;
    }

    public InetSocketAddress getAddress() {
        return address;
    }

    public void sendCommand(Command command) {
        command.handleOutgoing(server, this, new ArrayList<>());
    }

    public void sendCommand(Command command, List<String> args) {
        command.handleOutgoing(server, this, args);
    }


    public void sendJson(CommandBody body) {
        try (Socket socket = connect(); PrintWriter writer = new PrintWriter(socket.getOutputStream())) {
            writer.println(body.toJsonString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendJsonAndWaitForReply(CommandBody body) {
        try (Socket socket = connect();
             PrintWriter writer = new PrintWriter(socket.getOutputStream());
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            writer.println(body.toJsonString());
            writer.flush();

            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Socket connect() throws IOException {
        return new Socket(address.getAddress(), address.getPort());
    }

    @Override
    public String toString() {
        return "Client{" +
                "address=" + address +
                '}';
    }
}
