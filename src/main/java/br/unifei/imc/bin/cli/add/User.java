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

@CommandLine.Command(name = "user", mixinStandardHelpOptions = true, version = "1.0",
    description = "Add a new user", helpCommand = true, usageHelpAutoWidth = true,
    usageHelpWidth = 100, abbreviateSynopsis = true, sortOptions = false)

public class User implements Runnable {

  @CommandLine.Option(names = {"-n", "--name"}, description = "Users name", required = true)
  private String name;

  @CommandLine.Option(names = {"-p", "--password"}, description = "Users password", required = true)
  private String password;

  @CommandLine.Option(names = {"-e", "--email"}, description = "Users email", required = true)
  private String email;

  @CommandLine.Option(names = {"-t", "--type"}, description = "Users type", required = true)
  private String type;


  @Override
  public void run() {
    Dlog.log(getClass(), Options.INFO, "Adding user " + name);

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
      administrator.addNewUser(name, email, password, type);
    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
             InstantiationException | IllegalAccessException e) {
      Dlog.log(getClass(), Options.ERROR, "Error getting a user");
    }
  }
}
