package br.unifei.imc.bin.cli.init;

import br.unifei.imc.infrastructure.cache.Cache;
import br.unifei.imc.infrastructure.log.Dlog;
import br.unifei.imc.infrastructure.log.Options;
import picocli.CommandLine;

@CommandLine.Command(name = "database", mixinStandardHelpOptions = true, version = "1.0",
    description = "Initialize the database", helpCommand = true, usageHelpAutoWidth = true,
    usageHelpWidth = 100, abbreviateSynopsis = true, sortOptions = false)
public class Database implements Runnable {

  public void run() {
    Dlog.log(getClass(), Options.INFO, "Initializing the database");
    try {
      br.unifei.imc.infrastructure.database.Database.initialize();
    } catch (Exception e) {
      Dlog.log(getClass(), Options.ERROR, "Error initializing the database");
      e.printStackTrace();
    }
    Dlog.log(getClass(), Options.INFO, "Database initialized");

    Dlog.log(getClass(), Options.INFO, "Creating the administrator");

    try {
      br.unifei.imc.infrastructure.database.Database.createAdministrator();
    } catch (Exception e) {
      Dlog.log(getClass(), Options.ERROR, "Error creating the administrator");
      e.printStackTrace();
    }
    Dlog.log(getClass(), Options.INFO, "Administrator created");

    Cache.put("user", "admin@localhost");

    Dlog.log(getClass(), Options.INFO, "Cache initialized");
  }


}
