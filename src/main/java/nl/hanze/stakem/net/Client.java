package nl.hanze.stakem.net;

import nl.hanze.stakem.command.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
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
        sendCommand(command, new ArrayList<>());
    }

    public void sendCommand(Command command, List<String> args) {

    }

    public DatagramSocket connect() throws IOException {
        return new DatagramSocket(address.getPort(), address.getAddress());
    }

    @Override
    public String toString() {
        return "Client{" +
                "address=" + address +
                '}';
    }
}
