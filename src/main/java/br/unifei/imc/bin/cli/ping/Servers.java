package br.unifei.imc.bin.cli.ping;

import br.unifei.imc.infrastructure.database.Database;

import picocli.CommandLine;

import java.util.List;

@CommandLine.Command(name = "servers", mixinStandardHelpOptions = true, version = "1.0",
        description = "Ping all servers", helpCommand = true, usageHelpAutoWidth = true,
        usageHelpWidth = 100, abbreviateSynopsis = true, sortOptions = false)

public class Servers implements Runnable {
    @Override
    public void run() {
        List<String> serversList =  Database.getServers();
        if (serversList == null || serversList.size() == 0) {
            System.out.println("No servers found");
            return;
        }
        for (String server : serversList) {
            System.out.println(server);
        }
    }
}
