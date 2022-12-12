package br.unifei.imc;

import br.unifei.imc.bin.cli.Command;
import br.unifei.imc.bin.cli.CommandsAvailable;
import br.unifei.imc.infrastructure.cache.Cache;
import br.unifei.imc.infrastructure.database.Database;
import br.unifei.imc.infrastructure.log.Dlog;
import br.unifei.imc.infrastructure.log.Options;
import br.unifei.imc.utils.factory.Factory;
import java.lang.reflect.InvocationTargetException;
import picocli.CommandLine;

public class Main {

  public static void main(String[] args)
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException,
      InstantiationException, IllegalAccessException {
    if (args.length == 0) {
      System.out.println("JavaLI - Java Command Line Interface - v1.0");

      System.out.println("Type 'javalicli help' to see the available commands");

      System.out.println("Type 'javalicli help <command>' to see the available options"
          + " for a command");

      Dlog.log(Main.class, Options.INFO, "You are current logged as: " + Cache.get("user"));

      return;
    }

    String commandName = args[0];

    commandName = commandName.substring(0, 1).toUpperCase() + commandName.substring(1);
    CommandsAvailable command = CommandsAvailable.valueOf(commandName);
    Command commandInstance = Factory.getCommand(command);

    int rc = new CommandLine(commandInstance).execute(args);
    System.exit(rc);
  }
}
