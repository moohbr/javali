package br.unifei.imc.bin.cli.remove;

import br.unifei.imc.bin.cli.Command;
import picocli.CommandLine;


@CommandLine.Command(name = "remove", mixinStandardHelpOptions = true, version = "1.0",
    description = "Remove a thing", subcommands = {User.class, Server.class})
public class Remove extends Command implements Runnable {

  @CommandLine.Option(names = {"-v", "--verbose"},
      description = "Verbose mode. Helpful for troubleshooting.")
  boolean verboseMode;

  public void run() {
    System.out.println("Removing a thing");
  }

  public boolean isVerboseMode() {
    return verboseMode;
  }
}

