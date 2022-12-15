package br.unifei.imc.bin.cli.remove;


import br.unifei.imc.data.users.Administrator;
import br.unifei.imc.data.users.Types;
import br.unifei.imc.infrastructure.cache.Cache;
import br.unifei.imc.infrastructure.database.Database;
import br.unifei.imc.infrastructure.log.Dlog;
import br.unifei.imc.infrastructure.log.Options;
import br.unifei.imc.utils.factory.Factory;
import java.lang.reflect.InvocationTargetException;
import picocli.CommandLine;

/**
 * Remove server command.
 *
 * @author @moohbr
 *
 * @version 1.0
 * @since 1.0
 * @see br.unifei.imc.data.users.Administrator
 * @see br.unifei.imc.data.users.Types
 * @see br.unifei.imc.infrastructure.cache.Cache
 * @see br.unifei.imc.infrastructure.database.Database
 * @see br.unifei.imc.infrastructure.log.Dlog
 * @see br.unifei.imc.infrastructure.log.Options
 * @see br.unifei.imc.utils.factory.Factory
 * @see picocli.CommandLine
 * @see java.lang.reflect.InvocationTargetException
 */
@CommandLine.Command(name = "server", mixinStandardHelpOptions = true, version = "1.0",
    description = "Remove a server", helpCommand = true, usageHelpAutoWidth = true,
    usageHelpWidth = 100, abbreviateSynopsis = true, sortOptions = false)

public class Server implements Runnable {


  @CommandLine.Option(names = {"-n",
      "--hostname"}, description = "Server hostname", required = true)
  private String hostname;

  @Override
  public void run() {
    Dlog.log(getClass(), Options.INFO, "Removing a server");
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
      administrator.removeServer(hostname);
    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException
             | InstantiationException | IllegalAccessException e) {
      Dlog.log(getClass(), Options.ERROR, "Error getting a user");
    }
  }

}
