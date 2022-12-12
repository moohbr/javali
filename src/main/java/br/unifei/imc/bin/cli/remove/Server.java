package br.unifei.imc.bin.cli.remove;


import br.unifei.imc.infrastructure.log.Dlog;
import br.unifei.imc.infrastructure.log.Options;
import picocli.CommandLine;

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

  }

}
