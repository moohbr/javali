package br.unifei.imc.bin.cli.init;

import br.unifei.imc.bin.cli.Command;
import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(name = "init", description = "Initialize the database and cache",
    subcommands = {Database.class}, mixinStandardHelpOptions = true, version = "1.0")

public class Init extends Command implements Callable<Integer> {

  @Override
  public Integer call() {
    return 0;
  }
}

