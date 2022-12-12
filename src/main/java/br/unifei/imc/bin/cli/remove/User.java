package br.unifei.imc.bin.cli.remove;

import br.unifei.imc.data.users.Administrator;
import br.unifei.imc.infrastructure.cache.Cache;
import br.unifei.imc.infrastructure.log.Dlog;
import br.unifei.imc.infrastructure.log.Options;
import picocli.CommandLine;

@CommandLine.Command(name = "user", mixinStandardHelpOptions = true, version = "1.0",
    description = "Remove a user", helpCommand = true, usageHelpAutoWidth = true,
    usageHelpWidth = 100, abbreviateSynopsis = true, sortOptions = false)

public class User implements Runnable {

  @CommandLine.Option(names = {"-e", "--email"}, description = "Users email", required = true)
  private String email;

  @Override
  public void run() {
    Dlog.log(getClass(), Options.INFO, "Removing a user");

    String cachedUser = Cache.get("user");

    if (cachedUser == null) {
      Dlog.log(getClass(), Options.ERROR, "You must be logged in to remove a user");
      return;
    }

    String userType = cachedUser.getClass().getName();

    if (!userType.equals("br.unifei.imc.data.users.Administrator")) {
      Dlog.log(getClass(), Options.ERROR, "You must be an administrator to remove a user");
      return;
    }

   // Administrator administrator = (Administrator) cachedUser;


  }

}
