package br.unifei.imc.bin.cli.add;

import br.unifei.imc.infrastructure.log.DLog;
import br.unifei.imc.infrastructure.log.Options;
import br.unifei.imc.infrastructure.database.Database;

import picocli.CommandLine;

@CommandLine.Command(name = "user", mixinStandardHelpOptions = true, version = "1.0",
        description = "Add a new user", helpCommand = true, usageHelpAutoWidth = true,
        usageHelpWidth = 100, abbreviateSynopsis = true, sortOptions = false)

public class User implements Runnable {
    @CommandLine.ParentCommand
    Add parent;
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
        Database.addUser(name, password, email, type);
    }

}
