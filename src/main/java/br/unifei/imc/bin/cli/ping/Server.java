package br.unifei.imc.bin.cli.ping;

import br.unifei.imc.infrastructure.database.Database;
import br.unifei.imc.infrastructure.log.Dlog;
import br.unifei.imc.infrastructure.log.Options;
import java.net.InetAddress;
import picocli.CommandLine;

@CommandLine.Command(name = "server", mixinStandardHelpOptions = true, version = "1.0",
    description = "Add a new server", helpCommand = true, usageHelpAutoWidth = true,
    usageHelpWidth = 100, abbreviateSynopsis = true, sortOptions = false)

public class Server implements Runnable {

  @CommandLine.Option(names = {"-n", "--name"}, description = "Server hostname", required = true)
  private String hostname;


  @Override
  public void run() {
    Dlog.log(this.getClass(), Options.INFO, "Pinging the server " + hostname);
    if (Database.getServer(hostname)) {
      try {
        InetAddress address = InetAddress.getByName(hostname);
        if (address.isReachable(5000)) {
          Dlog.log(this.getClass(), Options.INFO, "Server is reachable");
        } else {
          Dlog.log(this.getClass(), Options.INFO, "Server is not reachable");
        }
      } catch (Exception e) {
        Dlog.log(this.getClass(), Options.ERROR,
            "Error while pinging the server: " + e.getMessage());
      }
    } else {
      Dlog.log(this.getClass(), Options.ERROR, "Server not found");
    }
  }
}
