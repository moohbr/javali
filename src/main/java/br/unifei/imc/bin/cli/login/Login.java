package br.unifei.imc.bin.cli.login;

import br.unifei.imc.bin.cli.Command;

import picocli.CommandLine;

@CommandLine.Command(name = "login", mixinStandardHelpOptions = true, version = "1.0",
    description = "Login in the CLI", subcommands = {User.class})
public class Login extends Command implements Runnable {

  @CommandLine.Option(names = {"-v", "--verbose"},
      description = "Verbose mode. Helpful for troubleshooting.")
  boolean verboseMode;

  public void run() {
    System.out.println("Adding a new thing");
  }

  public boolean isVerboseMode() {
    return verboseMode;
  }
}




