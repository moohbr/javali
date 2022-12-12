package br.unifei.imc.bin.cli.login;


import br.unifei.imc.bin.cli.Command;
import br.unifei.imc.infrastructure.cache.Cache;
import br.unifei.imc.infrastructure.database.Database;
import br.unifei.imc.infrastructure.log.Dlog;
import br.unifei.imc.infrastructure.log.Options;
import picocli.CommandLine;

@CommandLine.Command(name = "user", mixinStandardHelpOptions = true, version = "1.0",
    description = "Login with user", helpCommand = true, usageHelpAutoWidth = true,
    usageHelpWidth = 100, abbreviateSynopsis = true, sortOptions = false)

public class User extends Command implements Runnable {
  @CommandLine.Option(names = {"-e", "--email"}, description = "User email", required = true)
  private String email;

  @CommandLine.Option(names = {"-p", "--password"}, description = "User password", required = true)
  private String password;

  @Override
  public void run() {
    boolean logged =   Database.login(email, password);
    if (logged) {
      Dlog.log(this.getClass(), Options.INFO, "Login successful");
      Cache.put("user", email);
    } else {
      Dlog.log(this.getClass(), Options.ERROR, "Login failed");
    }
  }
}
