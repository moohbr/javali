package br.unifei.imc.bin.cli.add;

import br.unifei.imc.data.users.Administrator;
import br.unifei.imc.infrastructure.cache.Cache;
import br.unifei.imc.infrastructure.log.DLog;
import br.unifei.imc.infrastructure.log.Options;

import picocli.CommandLine;

@CommandLine.Command(name = "user", mixinStandardHelpOptions = true, version = "1.0",
        description = "Add a new user", helpCommand = true, usageHelpAutoWidth = true,
        usageHelpWidth = 100, abbreviateSynopsis = true, sortOptions = false)

public class User implements Runnable {
    Cache cache = Cache.getInstance();
    @CommandLine.Option(names = {"-n", "--name"}, description = "User name", required = true)
    private String name;

    @CommandLine.Option(names = {"-p", "--password"}, description = "User password", required = true)
    private String password;

    @CommandLine.Option(names = {"-e", "--email"}, description = "User email", required = true)
    private String email;

    @CommandLine.Option(names = {"-t", "--type"}, description = "User type", required = true)
    private String type;


    @Override
    public void run()  {
        DLog.log(getClass(), Options.INFO, "Adding user " + name);
        br.unifei.imc.data.users.User user = (br.unifei.imc.data.users.User) cache.get("user");

        if (user == null) {
            DLog.log(getClass(), Options.ERROR, "You must be logged in to add a user");
            return;
        }

        String userType = user.getClass().getName();

        if (!userType.equals("br.unifei.imc.data.users.Administrator")) {
            DLog.log(getClass(), Options.ERROR, "You must be an administrator to add a user");
            return;
        }

        Administrator administrator = (Administrator) user;

        administrator.addNewUser(name, password, email, type);
    }
}
