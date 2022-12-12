package br.unifei.imc.bin.cli.show;

import br.unifei.imc.infrastructure.database.Database;
import br.unifei.imc.infrastructure.log.Dlog;
import br.unifei.imc.infrastructure.log.Options;
import dnl.utils.text.table.TextTable;
import java.lang.reflect.Field;
import java.util.List;
import picocli.CommandLine;

@CommandLine.Command(name = "servers", mixinStandardHelpOptions = true, version = "1.0",
    description = "Show all servers", helpCommand = true, usageHelpAutoWidth = true,
    usageHelpWidth = 100, abbreviateSynopsis = true, sortOptions = false)

public class Server implements Runnable {
  final Field[] serverFields = br.unifei.imc.data.servers.Server.class.getDeclaredFields();
    @Override
    public void run() {

      String[] columnNames = new String[serverFields.length];

      for (int i = 0; i < serverFields.length; i++) {
        columnNames[i] = serverFields[i].getName();
      }

      Dlog.log(getClass(), Options.INFO, "Showing servers");

      List<br.unifei.imc.data.servers.Server> serverList = Database.getServers();

      if (serverList == null) {
        Dlog.log(getClass(), Options.ERROR, "No users found");
        return;
      }

      String[][] data = new String[serverList.size()][serverFields.length];


      for (int i = 0; i < serverList.size(); i++) {
        for (int j = 0; j < serverFields.length; j++) {
          try {
            serverFields[j].setAccessible(true);
            // Verify if the field is the password
            if (serverFields[j].getName().equals("password")) {
              data[i][j] = "********";
              continue;
            }
            if (serverFields[j].get(serverList.get(i)) != null) {
              data[i][j] = serverFields[j].get(serverList.get(i)).toString();
            } else {
              data[i][j] = "null";
            }
          } catch (IllegalAccessException e) {
            Dlog.log(getClass(), Options.ERROR, "Error getting server data");
            data[i][j] = "N/A";
          }
        }
      }

      TextTable table = new TextTable(columnNames, data);

      table.printTable();

      Dlog.log(getClass(), Options.INFO, "Servers shown");

    }
}

