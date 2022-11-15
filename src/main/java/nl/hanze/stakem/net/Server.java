package nl.hanze.stakem.net;

import com.simtechdata.waifupnp.UPnP;
import nl.hanze.stakem.Constants;
import nl.hanze.stakem.command.CommandFactory;
import nl.hanze.stakem.command.CommandHandler;

import java.net.*;
import java.util.*;

public class Server {

    private int port;
    private ServerSocket serverSocket;
    private boolean isRootNode = false;
    private final Random random = new Random();
    private boolean isStopping = false;

    private final Map<InetSocketAddress, Client> clients = new HashMap<>();

    public Server(int port) {
        this.port = port;
    }

    public Server(int port, boolean isRootNode) {
        this.port = port;
        this.isRootNode = isRootNode;
    }

    public void start() {
        try {
            boolean uPnPAvailable = UPnP.isUPnPAvailable();

            if (uPnPAvailable && UPnP.isMappedTCP(port)) {
                throw new ConnectException("Port is already mapped");
            }

            serverSocket = new ServerSocket(port);

            if (uPnPAvailable) {
                UPnP.openPortTCP(port);
            }

            addShutdownHook();
            new Thread(this::listen).start();
            contactRootNode();
            System.out.println("Started a node at port " + port);
        } catch (ConnectException | BindException e) {
            System.out.println("Port " + port + " is in use, retrying with a different port...");
            port = random.nextInt(Constants.RANDOM_PORT_RANGE_START, Constants.RANDOM_PORT_RANGE_END);
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void contactRootNode() {
        if (isRootNode) {
            return;
        }

        Client client = createAndAddClient(new InetSocketAddress(Constants.ROOT_NODE_HOSTNAME, Constants.ROOT_NODE_PORT));

        try {
            client.sendCommand(CommandFactory.getCommand("register"));
        } catch (Exception e) {
            System.out.println("Failed to contact root node! Exiting...");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void listen() {
        while (!isStopping) {
            try {
                Socket socket = serverSocket.accept();
                CommandHandler handler = new CommandHandler(this, socket);

                handler.handle();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        try {
            if (UPnP.isUPnPAvailable()) {
                UPnP.closePortTCP(port);
            }

            isStopping = true;
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addClient(InetSocketAddress address) {
        if (!clients.containsKey(address)) {
            clients.put(address, new Client(this, address));
        }
    }

    public Client createAndAddClient(InetSocketAddress address) {
        if (!clients.containsKey(address)) {
            Client client = new Client(this, address);
            clients.put(address, client);
            return client;
        } else {
            return clients.get(address);
        }
    }

    public Collection<Client> getClients() {
        return clients.values();
    }

    public void removeClient(InetSocketAddress address) {
        clients.remove(address);
    }

    private void addShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    public int getPort() {
        return port;
    }
}
