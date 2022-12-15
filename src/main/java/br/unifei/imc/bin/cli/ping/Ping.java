package br.unifei.imc.bin.cli.ping;

import br.unifei.imc.bin.cli.Command;
import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(name = "ping", description = "Ping a server or all server",
    subcommands = {Server.class, Servers.class}, mixinStandardHelpOptions = true, version = "1.0")
public class Ping extends Command implements Callable<Integer> {

  @Override
  public Integer call() {
    return 1;
  }
}


