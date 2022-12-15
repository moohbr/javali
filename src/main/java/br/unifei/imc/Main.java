package br.unifei.imc;

import br.unifei.imc.bin.cli.Command;
import br.unifei.imc.bin.cli.CommandsAvailable;
import br.unifei.imc.infrastructure.cache.Cache;
import br.unifei.imc.infrastructure.log.Dlog;
import br.unifei.imc.infrastructure.log.Options;
import br.unifei.imc.utils.factory.Factory;
import java.lang.reflect.InvocationTargetException;
import picocli.CommandLine;

/**
 * Start the application.
 *
 * @author @moohbr
 *
 * @version 1.0
 * @since 1.0
 * @see br.unifei.imc.bin.cli.Command
 * @see br.unifei.imc.bin.cli.CommandsAvailable
 * @see br.unifei.imc.infrastructure.cache.Cache
 * @see br.unifei.imc.infrastructure.log.Dlog
 * @see br.unifei.imc.infrastructure.log.Options
 * @see br.unifei.imc.utils.factory.Factory
 * @see picocli.CommandLine
 * @see java.lang.reflect.InvocationTargetException
 */

public class Main {

  /**
    * Start the application.
    *
    * @param args the command line arguments
    *
    * @see java.lang.Exception
    * @see java.lang.reflect.InvocationTargetException
    * @see picocli.CommandLine
    * @see br.unifei.imc.bin.cli.Command
    * @see br.unifei.imc.bin.cli.CommandsAvailable
    *
    * @since 1.0
    */
  public static void main(String[] args) {
    try {
      if (args.length == 0) {
        System.out.println("JavaLI - Java Command Line Interface - v1.0");

        Dlog.log(Main.class, Options.INFO, "You are current logged as: " + Cache.get("user"));

        return;
      }

      if (args[0].equals("logout")) {
        Cache.remove("user");
        return;
      }

      String commandNameBrute = args[0];

      String commandName = commandNameBrute.substring(0, 1).toUpperCase()
          + commandNameBrute.substring(1);

      CommandsAvailable command = CommandsAvailable.valueOf(commandName);

      Command commandInstance = Factory.getCommand(command);

      int rc = new CommandLine(commandInstance).execute(args);
      System.exit(rc);
    } catch (Exception e) {
      Dlog.log(Main.class, Options.ERROR, e.getMessage());
    }
  }
}
