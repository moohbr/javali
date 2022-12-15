package br.unifei.imc.bin.cli.add;

import br.unifei.imc.data.users.Administrator;
import br.unifei.imc.data.users.Types;
import br.unifei.imc.infrastructure.cache.Cache;
import br.unifei.imc.infrastructure.database.Database;
import br.unifei.imc.infrastructure.log.Dlog;
import br.unifei.imc.infrastructure.log.Options;
import br.unifei.imc.utils.factory.Factory;
import java.lang.reflect.InvocationTargetException;
import picocli.CommandLine;

@CommandLine.Command(name = "server", mixinStandardHelpOptions = true, version = "1.0",
    description = "Add a new server", helpCommand = true, usageHelpAutoWidth = true,
    usageHelpWidth = 100, abbreviateSynopsis = true, sortOptions = false)

public class Server implements Runnable {

  @CommandLine.Option(names = {"-n", "--name"}, description = "Server hostname", required = true)
  private String hostname;

  @CommandLine.Option(names = {"-u", "--url"}, description = "Server url", required = true)
  private String url;


  @CommandLine.Option(names = {"-i", "--ip"}, description = "Server ip", required = true)
  private String ip;

  @CommandLine.Option(names = {"-p", "--port"}, description = "Server port", required = true)
  private Integer port;

  @CommandLine.Option(names = {"-m",
      "--monitor"}, description = "Do you want monitor this server?", required = true)
  private Boolean monitor;

  public Server() {
  }


  @Override
  public void run() {
    Dlog.log(getClass(), Options.INFO, "Adding a new server");

    String administratorEmail = Cache.get("user");

    String userProperties = Database.getUser(administratorEmail);

    if (userProperties == null) {
      Dlog.log(getClass(), Options.ERROR, "User not found");
      return;
    }

    String[] properties = userProperties.split(" ");

    String userType = properties[3].substring(0, properties[3].length() - 1);

    if (!userType.equals(Types.Administrator.toString())) {
      Dlog.log(getClass(), Options.ERROR, "User is not an administrator");
      return;
    }

    try {
      Administrator administrator = (Administrator) Factory.createUser(Types.Administrator);
      administrator.addNewServer(hostname, url, ip, port, monitor);
    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException
             | InstantiationException | IllegalAccessException e) {
      Dlog.log(getClass(), Options.ERROR, e.getMessage());
    }
  }
}
