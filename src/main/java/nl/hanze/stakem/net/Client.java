package nl.hanze.stakem.net;

import nl.hanze.stakem.command.Command;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

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
        server.sendCommand(command, address);
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
