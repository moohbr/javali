package br.unifei.imc.bin.cli.add;

import br.unifei.imc.bin.cli.Command;

import picocli.CommandLine;

@CommandLine.Command(name = "add", mixinStandardHelpOptions = true, version = "1.0",
        description = "Add a new thing", subcommands = {User.class, Server.class})
public class Add extends Command implements Runnable {
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

