package br.unifei.imc.bin.cli.ping;

import br.unifei.imc.bin.cli.Command;
import br.unifei.imc.infrastructure.database.Database;
import br.unifei.imc.infrastructure.log.DLog;
import br.unifei.imc.infrastructure.log.Options;

import java.util.List;
import java.util.concurrent.Callable;

import picocli.CommandLine;
@CommandLine.Command(name = "ping", description = "Ping a server or all server",
        subcommands = {Server.class, Servers.class}, mixinStandardHelpOptions = true, version = "1.0")
public class Ping extends Command implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "Server name")
    private String serverName;
    @Override
    public Integer call() throws Exception {
        List<String> serversList =  Database.getServers();
        if (serversList == null || serversList.size() == 0) {
            DLog.log(this.getClass(), Options.INFO, "No servers found");
            return 1;
        }
        for (String server : serversList) {
            System.out.println(server);
        }
            return 0;
    }
}


