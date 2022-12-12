package br.unifei.imc.bin.cli.remove;

import br.unifei.imc.infrastructure.cache.Cache;
import br.unifei.imc.infrastructure.log.DLog;
import br.unifei.imc.infrastructure.log.Options;


import picocli.CommandLine;

@CommandLine.Command(name = "server", mixinStandardHelpOptions = true, version = "1.0",
        description = "Remove a server", helpCommand = true, usageHelpAutoWidth = true,
        usageHelpWidth = 100, abbreviateSynopsis = true, sortOptions = false)

public class Server implements Runnable {

    Cache cache = Cache.getInstance();

    br.unifei.imc.data.users.User user = (br.unifei.imc.data.users.User) cache.get("user");

    @CommandLine.Option(names = {"-n", "--hostname"}, description = "Server hostname", required = true)
    private String hostname;

    @Override
    public void run()  {
        DLog.log(getClass(), Options.INFO, "Removing a server");

        if (user == null) {
            DLog.log(getClass(), Options.ERROR, "You must be logged in to remove a user");
            return;
        }

        String userType = user.getClass().getName();

        if (!userType.equals("br.unifei.imc.data.users.Administrator")) {
            DLog.log(getClass(), Options.ERROR, "You must be an administrator to remove a user");
            return;
        }

        br.unifei.imc.data.users.Administrator administrator = (br.unifei.imc.data.users.Administrator) user;

        administrator.removeServer(hostname);
    }

}
