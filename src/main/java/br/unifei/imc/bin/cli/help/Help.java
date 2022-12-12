package br.unifei.imc.bin.cli.help;

import br.unifei.imc.bin.cli.Command;
import picocli.CommandLine;

@CommandLine.Command(name = "help", mixinStandardHelpOptions = true, version = "1.0",
    description = "Help for the CLI", helpCommand = true)
public class Help extends Command implements Runnable {

  public void run() {
    System.out.println("Help for the CLI");
  }
}
