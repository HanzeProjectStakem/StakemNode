package nl.hanze.stakem.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationProperties
@ConfigurationPropertiesScan
public class NodeConfig {

    private String protocolVersion;

    private int defaultNodePort;

    private int randomPortRangeStart;

    private int randomPortRangeEnd;

    private String rootNodeHostname;

    private int rootNodePort;

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public int getDefaultNodePort() {
        return defaultNodePort;
    }

    public void setDefaultNodePort(int defaultNodePort) {
        this.defaultNodePort = defaultNodePort;
    }

    public int getRandomPortRangeStart() {
        return randomPortRangeStart;
    }

    public void setRandomPortRangeStart(int randomPortRangeStart) {
        this.randomPortRangeStart = randomPortRangeStart;
    }

    public int getRandomPortRangeEnd() {
        return randomPortRangeEnd;
    }

    public void setRandomPortRangeEnd(int randomPortRangeEnd) {
        this.randomPortRangeEnd = randomPortRangeEnd;
    }

    public String getRootNodeHostname() {
        return rootNodeHostname;
    }

    public void setRootNodeHostname(String rootNodeHostname) {
        this.rootNodeHostname = rootNodeHostname;
    }

    public int getRootNodePort() {
        return rootNodePort;
    }

    public void setRootNodePort(int rootNodePort) {
        this.rootNodePort = rootNodePort;
    }
}
