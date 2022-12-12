package br.unifei.imc;

import br.unifei.imc.bin.cli.Command;
import br.unifei.imc.data.users.User;
import br.unifei.imc.utils.factory.Factory;
import br.unifei.imc.bin.cli.CommandsAvailable;
import br.unifei.imc.infrastructure.database.Database;

import br.unifei.imc.data.users.Types;

import picocli.CommandLine;


import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
            if (args.length == 0) {
                System.out.println("JavaLI - Java Command Line Interface - v1.0");
                return;
            }

            Database.initialize();

            String commandName = args[0];

            commandName = commandName.substring(0, 1).toUpperCase() + commandName.substring(1);
            CommandsAvailable command = CommandsAvailable.valueOf(commandName);
            Command commandInstance = Factory.getCommand(command);

            int rc = new CommandLine(commandInstance).execute(args);
            System.exit(rc);
    }
}
