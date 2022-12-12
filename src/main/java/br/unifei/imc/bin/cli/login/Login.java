package br.unifei.imc.bin.cli.login;

import br.unifei.imc.bin.cli.Command;

import br.unifei.imc.data.users.User;
import br.unifei.imc.infrastructure.cache.Cache;
import br.unifei.imc.infrastructure.database.Database;
import picocli.CommandLine;


import java.util.concurrent.Callable;

@CommandLine.Command(name = "login", description = "Login to the system")
public class Login extends Command implements Callable<Integer> {

    Cache cache = Cache.getInstance();
    User user = new User();

    @CommandLine.Option(names = {"-u", "--username"}, description = "Username", required = true)
    private String name;

    @CommandLine.Option(names = {"-p", "--password"}, description = "Password", required = true)
    private String password;

    @Override
    public Integer call() {
        user.setName(name);
        user.setPassword(password);

        if (Database.login(name, password)) {
            cache.add("user", user);
            return 0;
        }

        return 1;
    }

}


