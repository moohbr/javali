package br.unifei.imc.bin.cli.remove;

import br.unifei.imc.infrastructure.log.DLog;
import br.unifei.imc.infrastructure.log.Options;
import br.unifei.imc.infrastructure.cache.Cache;

import picocli.CommandLine;

@CommandLine.Command(name = "user", mixinStandardHelpOptions = true, version = "1.0",
        description = "Remove a user", helpCommand = true, usageHelpAutoWidth = true,
        usageHelpWidth = 100, abbreviateSynopsis = true, sortOptions = false)

public class User implements Runnable {

    @CommandLine.Option(names = {"-e", "--email"}, description = "User email", required = true)
    private String email;

    @Override
    public void run()  {
        DLog.log(getClass(), Options.INFO, "Removing a user");

        Cache cache = Cache.getInstance();

        br.unifei.imc.data.users.User user = (br.unifei.imc.data.users.User) cache.get("user");

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

        administrator.removeUser(email);


    }

}
