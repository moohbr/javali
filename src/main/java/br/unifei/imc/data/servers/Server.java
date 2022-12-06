package br.unifei.imc.data.servers;

import lombok.Getter;
import lombok.Setter;

// All server has a Hostname, URL, IP, Port, Last Status, Last Update, Last Check and Monitor
public class Server {
    @Getter @Setter private String hostname;
    @Getter @Setter private String url;
    @Getter @Setter private String ip;
    @Getter @Setter private String port;
    @Getter @Setter private String lastStatus;
    @Getter @Setter private String lastUpdate;
    @Getter @Setter private String lastCheck;
    @Getter @Setter private String monitor;

    public Server(String hostname, String url, String ip, String port, String lastUpdate, String monitor) {
        this.hostname = hostname;
        this.url = url;
        this.ip = ip;
        this.port = port;
        this.lastStatus = null;
        this.lastUpdate = lastUpdate;
        this.lastCheck = null;
        this.monitor = monitor;
    }
}
