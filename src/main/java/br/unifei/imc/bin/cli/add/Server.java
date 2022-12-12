package br.unifei.imc.bin.cli.add;

import br.unifei.imc.data.users.Administrator;
import br.unifei.imc.data.users.User;
import br.unifei.imc.infrastructure.cache.Cache;
import br.unifei.imc.infrastructure.log.DLog;
import br.unifei.imc.infrastructure.log.Options;

import picocli.CommandLine;

@CommandLine.Command(name = "server", mixinStandardHelpOptions = true, version = "1.0",
        description = "Add a new server", helpCommand = true, usageHelpAutoWidth = true,
        usageHelpWidth = 100, abbreviateSynopsis = true, sortOptions = false)

public class Server implements Runnable {
    Cache cache = Cache.getInstance();
    @CommandLine.Option(names = {"-n", "--name"}, description = "Server hostname", required = true)
    private String hostname;

    @CommandLine.Option(names = {"-u", "--url"}, description = "Server url", required = true)
    private String url;


    @CommandLine.Option(names = {"-i", "--ip"}, description = "Server ip", required = true)
    private String ip;

    @CommandLine.Option(names = {"-p", "--port"}, description = "Server port", required = true)
    private Integer port;

    @CommandLine.Option(names = {"-m", "--monitor"}, description = "Do you want monitor this server?", required = true)
    private Boolean monitor;

    public Server() {
    }


    @Override
    public void run()  {
        DLog.log(getClass(), Options.INFO, "Adding a new server");

        User user = (User) cache.get("user");

        if (user == null) {
            DLog.log(getClass(), Options.ERROR, "You must be logged in to add a server");
            return;
        }

        String userType = user.getClass().getName();

        if (!userType.equals("br.unifei.imc.data.users.Administrator")) {
            DLog.log(getClass(), Options.ERROR, "You must be an administrator to add a user");
            return;
        }

        Administrator administrator = (Administrator) user;

        administrator.addNewServer(hostname, url, ip, port, monitor);
    }
}
