package nl.hanze.stakem.net;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simtechdata.waifupnp.UPnP;
import nl.hanze.stakem.Constants;
import nl.hanze.stakem.event.MessageEventPublisher;
import nl.hanze.stakem.net.messages.RegisterMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.*;
import java.util.*;

public class Server {

    private final Random random = new Random();
    private final Map<InetSocketAddress, Client> clients = new HashMap<>();
    private final Deque<DatagramPacket> packetQueue = new ArrayDeque<>();
    private MessageEventPublisher messageEventPublisher;
    private int port;
    private DatagramSocket serverSocket;
    private boolean isRootNode = false;
    private boolean isStopping = false;
    private NodeState state = NodeState.STARTING;

    public Server(int port) {
        this.port = port;
    }

    public Server(int port, boolean isRootNode) {
        this.port = port;
        this.isRootNode = isRootNode;
    }

    @Autowired
    public void setMessageEventPublisher(MessageEventPublisher messageEventPublisher) {
        this.messageEventPublisher = messageEventPublisher;
    }

    public boolean isRootNode() {
        return isRootNode;
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
                    MessageBody messageBody = mapper.readValue(jsonString, MessageBody.class);

                    if (!messageBody.getVersion().equals(Constants.VERSION)) {
                        System.out.println("Received a message with an incompatible version, ignoring...");
                        continue;
                    }

                    messageEventPublisher.publishEvent(this, messageBody, packet);
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

        // TODO: implement

        state = NodeState.READY;
    }

    private void contactRootNode() {
        state = NodeState.STARTING;

        Client client = createAndAddClient(new InetSocketAddress(Constants.ROOT_NODE_HOSTNAME, Constants.ROOT_NODE_PORT));

        try {
            client.sendMessage(new RegisterMessage());
        } catch (Exception e) {
            System.out.println("Failed to contact root node! Exiting...");
            e.printStackTrace();
            System.exit(1);
        }

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
                UPnP.closePortUDP(port);
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

    public List<InetSocketAddress> getClientAddresses() {
        return new ArrayList<>(clients.keySet());
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

    public void sendMessage(Message message, InetSocketAddress address) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            MessageBody body = new MessageBody(message, this);
            byte[] payload = mapper.writeValueAsBytes(body);

            DatagramPacket packet = new DatagramPacket(payload, payload.length, address);
            serverSocket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
