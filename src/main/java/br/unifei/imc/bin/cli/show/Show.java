package br.unifei.imc.bin.cli.show;

import br.unifei.imc.bin.cli.Command;
import picocli.CommandLine;

@CommandLine.Command(name = "show", mixinStandardHelpOptions = true, version = "1.0",
    description = "Show servers or users", subcommands = {Users.class, Server.class})
public class Show extends Command implements Runnable{
}
