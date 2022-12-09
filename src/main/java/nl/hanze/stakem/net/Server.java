package nl.hanze.stakem.net;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simtechdata.waifupnp.UPnP;
import nl.hanze.stakem.Constants;
import nl.hanze.stakem.command.Command;
import nl.hanze.stakem.command.CommandBody;
import nl.hanze.stakem.event.Event;
import nl.hanze.stakem.event.EventFactory;
import nl.hanze.stakem.event.EventManager;

import java.net.*;
import java.util.*;

public class Server {

    private final Random random = new Random();
    private final Map<InetSocketAddress, Client> clients = new HashMap<>();
    private int port;
    private DatagramSocket serverSocket;
    private boolean isRootNode = false;
    private boolean isStopping = false;
    private NodeState state = NodeState.STARTING;
    private Deque<DatagramPacket> packetQueue = new ArrayDeque<>();

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

            if (uPnPAvailable && UPnP.isMappedUDP(port)) {
                throw new ConnectException("Port is already mapped");
            }

            serverSocket = new DatagramSocket(port);

            if (uPnPAvailable) {
                UPnP.openPortUDP(port);
            }

            addShutdownHook();
            new Thread(this::listen).start();
            new Thread(this::startQueueProcessThread).start();

            if (!isRootNode) {
                contactRootNode();
            } else {
                synchronizeBlockchain();
            }

            System.out.println("Started a node at port " + port);
        } catch (ConnectException | BindException e) {
            System.out.println("Port " + port + " is in use, retrying with a different port...");
            port = random.nextInt(Constants.RANDOM_PORT_RANGE_START, Constants.RANDOM_PORT_RANGE_END);
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startQueueProcessThread() {
        ObjectMapper mapper = new ObjectMapper();

        while (!isStopping) {
            try {
                if (!packetQueue.isEmpty()) {
                    DatagramPacket packet = packetQueue.pop();
                    String jsonString = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("Received message: " + jsonString);
                    CommandBody commandBody = mapper.readValue(jsonString, CommandBody.class);

                    if (!commandBody.getVersion().equals(Constants.VERSION)) {
                        System.out.println("Received a command with an incompatible version, ignoring...");
                        continue;
                    }

                    Event event = EventFactory.getEvent(commandBody, packet);

                    EventManager.getInstance().dispatchEvent(event);
                    addClient((InetSocketAddress) packet.getSocketAddress());
                } else {
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void synchronizeBlockchain() {
        state = NodeState.SYNCING;


        state = NodeState.READY;
    }

    private void contactRootNode() {
        state = NodeState.STARTING;


        synchronizeBlockchain();
    }

    public void listen() {
        byte[] buffer = new byte[1024];

        while (!isStopping) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(packet);

                packetQueue.add(packet);
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

    public Client getClient(InetSocketAddress address) {
        return clients.get(address);
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

    public NodeState getState() {
        return state;
    }

    public void sendCommand(Command command, InetSocketAddress address) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            CommandBody body = new CommandBody(command);
            byte[] payload = mapper.writeValueAsBytes(body);

            DatagramPacket packet = new DatagramPacket(payload, payload.length, address);
            serverSocket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
