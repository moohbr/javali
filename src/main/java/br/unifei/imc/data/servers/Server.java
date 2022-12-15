package br.unifei.imc.data.servers;

import lombok.Getter;
import lombok.Setter;

// All server has a Hostname, URL, IP, Port, Last Status, Last Update, Last Check and Monitor
public class Server {

  @Getter
  @Setter
  private String hostname;
  @Getter
  @Setter
  private String url;
  @Getter
  @Setter
  private String ip;
  @Getter
  @Setter
  private Integer port;
  @Getter
  @Setter
  private Integer lastStatus;
  @Getter
  @Setter
  private String monitor;
  @Getter
  @Setter
  private String lastCheck;
  @Getter
  @Setter
  private String lastUpdate;


  public Server(String hostname, String url, String ip, Integer port, String lastUpdate,
      String monitor) {
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
