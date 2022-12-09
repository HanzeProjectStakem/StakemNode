package nl.hanze.stakem;

public class Constants {

    public static final String VERSION = "0.1";
    // TODO: move these values to a config files
    public static final int DEFAULT_PORT = 52145;
    // For when DEFAULT_PORT is in use
    public static final int RANDOM_PORT_RANGE_START = 49152;
    public static final int RANDOM_PORT_RANGE_END = 65535;
    public static final String ROOT_NODE_HOSTNAME = "94.212.83.107";
    public static final int ROOT_NODE_PORT = DEFAULT_PORT;

    private Constants() {
    }

}
