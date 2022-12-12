package br.unifei.imc.bin.cli.show;

import br.unifei.imc.infrastructure.database.Database;
import br.unifei.imc.infrastructure.log.Dlog;
import br.unifei.imc.infrastructure.log.Options;
import dnl.utils.text.table.TextTable;
import java.lang.reflect.Field;
import java.util.List;
import picocli.CommandLine;

@CommandLine.Command(name = "users", mixinStandardHelpOptions = true, version = "1.0",
    description = "Show all users", helpCommand = true, usageHelpAutoWidth = true,
    usageHelpWidth = 100, abbreviateSynopsis = true, sortOptions = false)

public class Users implements Runnable {

  final Field[] userField = br.unifei.imc.data.users.User.class.getDeclaredFields();

  @Override
  public void run() {

    String[] columnNames = new String[userField.length];

    for (int i = 0; i < userField.length; i++) {
      columnNames[i] = userField[i].getName();
    }

    Dlog.log(getClass(), Options.INFO, "Showing users");

    List<br.unifei.imc.data.users.User> userList = Database.getUsers();

    if (userList == null) {
      Dlog.log(getClass(), Options.ERROR, "No users found");
      return;
    }

    String[][] data = new String[userList.size()][userField.length];

    for (int i = 0; i < userList.size(); i++) {
      for (int j = 0; j < userField.length; j++) {
        try {
          userField[j].setAccessible(true);
          // Verify if the field is the password
          if (userField[j].getName().equals("password")) {
            data[i][j] = "********";
            continue;
          }
          if (userField[j].get(userList.get(i)) != null) {
            data[i][j] = userField[j].get(userList.get(i)).toString();
          } else {
            data[i][j] = "null";
          }
        } catch (IllegalAccessException e) {
          Dlog.log(getClass(), Options.ERROR, "Error getting user data");
          data[i][j] = "N/A";
        }
      }
    }

    TextTable table = new TextTable(columnNames, data);

    table.printTable();

    Dlog.log(getClass(), Options.INFO, "Users shown");

  }
}
