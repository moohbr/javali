package br.unifei.imc.bin.cli.ping;

import br.unifei.imc.data.servers.Server;
import br.unifei.imc.infrastructure.database.Database;
import br.unifei.imc.infrastructure.log.Dlog;
import br.unifei.imc.infrastructure.log.Options;
import java.net.InetAddress;
import java.util.List;
import picocli.CommandLine;

@CommandLine.Command(name = "servers", mixinStandardHelpOptions = true, version = "1.0",
    description = "Ping all servers", helpCommand = true, usageHelpAutoWidth = true,
    usageHelpWidth = 100, abbreviateSynopsis = true, sortOptions = false)

public class Servers implements Runnable {

  @Override
  public void run() {
    List<Server> listServers = Database.getServers();

    if (listServers == null) {
      Dlog.log(this.getClass(), Options.ERROR, "No servers found");
      return;
    }
    for (Server server : listServers) {
      Dlog.log(this.getClass(), Options.INFO, "Pinging server " + server.getHostname());

      try {
        InetAddress address = InetAddress.getByName(server.getHostname());
        if (address.isReachable(5000)) {
          Dlog.log(this.getClass(), Options.INFO, "Server " + server.getHostname()
              + " is reachable");
          Database.updateServerStatus(server.getHostname(), 200);
        } else {
          Dlog.log(this.getClass(), Options.INFO, "Server " + server.getHostname()
              + " is not reachable");
          Database.updateServerStatus(server.getHostname(), 500);
        }
      } catch (Exception e) {
        Dlog.log(this.getClass(), Options.ERROR, "Error pinging server "
            + server.getHostname());
      }

    }

  }
}
