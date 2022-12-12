package br.unifei.imc.infrastructure.database;

import br.unifei.imc.data.DataTypes;
import br.unifei.imc.data.users.Types;
import br.unifei.imc.data.users.User;
import br.unifei.imc.infrastructure.configuration.Configuration;
import br.unifei.imc.infrastructure.configuration.EnvironmentVariables;
import br.unifei.imc.infrastructure.log.Dlog;
import br.unifei.imc.infrastructure.log.Options;
import br.unifei.imc.utils.factory.Factory;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

// Implements the singleton pattern
public class Database {

  private static final String databaseName = Configuration.getEnvironmentVariable(
      EnvironmentVariables.SQL_FILE_NAME);
  private static Database instance;
  private static Connection connection;

  private Database() {
    try {
      Class.forName("org.sqlite.JDBC");

      connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);

      Dlog.log(Database.class, Options.INFO, "Database connection established.");
      Thread.sleep(1000);

    } catch (ClassNotFoundException | SQLException | InterruptedException e) {
      Dlog.log(Database.class, Options.ERROR, e.getMessage());
    }
  }

  public static void initialize() {
    if (instance == null) {
      instance = new Database();
      initializeTables();
    }
  }

  public static void initializeTables() {
    Dlog.log(Database.class, Options.INFO, "Initializing tables...");
    try {
      Statement statement = connection.createStatement();

      String dataPackage = "br.unifei.imc.data";

      for (DataTypes type : DataTypes.values()) {
        String tableName = type.toString();
        String className =
            dataPackage + "." + tableName + "." + tableName.substring(0, 1).toUpperCase()
                + tableName.substring(1);
        className = className.substring(0, className.length() - 1);

        Class<?> clazz = Class.forName(className);
        Field[] fields = clazz.getDeclaredFields();

        StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (");

        for (Field field : fields) {
          String fieldName = field.getName();
          String fieldType = field.getType().getSimpleName();

          query.append(fieldName).append(" ").append(fieldType).append(", ");
        }

        query.deleteCharAt(query.length() - 1);
        query.deleteCharAt(query.length() - 1);
        query.append(")");

        statement.executeUpdate(query.toString());
      }

      statement.close();
    } catch (SQLException | ClassNotFoundException e) {
      Dlog.log(Database.class, Options.ERROR, e.getMessage());
    }
  }

  public static void addUser(String name, String email, String password, String role) {
    try {
      if (connection == null) {
        Dlog.log(Database.class, Options.ERROR, "Database connection is null");
        connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
      }
      Statement statement = connection.createStatement();
      // query if user not exists
      String query =
          "SELECT * FROM users WHERE name = '" + name + "'" + " OR email = '" + email + "'";
      ResultSet resultSet = statement.executeQuery(query);

      if (resultSet.next()) {
        Dlog.log(Database.class, Options.ERROR, "Users already exists");
        return;
      }

      String lastUpdate = String.valueOf(Instant.now());

      query = "INSERT INTO users (name, email, password, type, lastUpdate) VALUES ('" + name
          + "', '" + email + "', '" + password + "', '" + role + "', '" + lastUpdate + "')";

      statement.executeUpdate(query);
      statement.close();
      Dlog.log(Database.class, Options.INFO, "Users " + name + " added successfully.");
    } catch (SQLException e) {
      Dlog.log(Database.class, Options.ERROR, e.getMessage());
    }
  }

  public static void removeUser(String email) {
    try {
      Statement statement = connection.createStatement();
      // query if user not exists
      String query = "SELECT * FROM users WHERE email = '" + email + "'";
      ResultSet resultSet = statement.executeQuery(query);

      if (!resultSet.next()) {
        Dlog.log(Database.class, Options.ERROR, "Users does not exists");
        return;
      }

      query = "DELETE FROM users WHERE email = '" + email + "'";

      statement.executeUpdate(query);
      statement.close();
      Dlog.log(Database.class, Options.INFO, "Users " + email + " removed successfully.");
    } catch (SQLException e) {
      Dlog.log(Database.class, Options.ERROR, e.getMessage());
    }
  }

  public static void changeUserPassword(String name, String oldPassword, String newPassword) {
    try {
      Statement statement = connection.createStatement();
      // query if user exists
      String query =
          "SELECT * FROM users WHERE name = '" + name + "'" + " AND password = '" + oldPassword
              + "'";
      ResultSet resultSet = statement.executeQuery(query);

      if (!resultSet.next()) {
        Dlog.log(Database.class, Options.ERROR, "Users does not exists");
        return;
      }

      query = "UPDATE users SET password = '" + newPassword + "' WHERE name = '" + name + "'";

      statement.executeUpdate(query);
      statement.close();
      Dlog.log(Database.class, Options.INFO, "Users " + name
          + " password changed successfully.");
    } catch (Exception e) {
      Dlog.log(Database.class, Options.ERROR, e.getMessage());
    }
  }

  public static void addServer(String hostname, String url, String ip, Integer port,
      Boolean monitor) {
    try {
      Statement statement = connection.createStatement();
      // query if user not exists
      String query =
          "SELECT * FROM servers WHERE hostname = '" + hostname + "'" + " OR url = '" + url + "'"
              + " OR ip = '" + ip + "'";
      ResultSet resultSet = statement.executeQuery(query);

      if (resultSet.next()) {
        Dlog.log(Database.class, Options.ERROR, "Server already exists");
        return;
      }

      String lastUpdate = String.valueOf(Instant.now());

      query = "INSERT INTO servers (hostname, url, ip, port, monitor, lastUpdate) VALUES ('"
          + hostname + "', '" + url + "', '" + ip + "', '" + port + "', '" + monitor + "', '"
          + lastUpdate + "')";

      statement.executeUpdate(query);
      statement.close();
      Dlog.log(Database.class, Options.INFO, "Server " + hostname + " added successfully.");
    } catch (SQLException e) {
      Dlog.log(Database.class, Options.ERROR, e.getMessage());
    }
  }

  public static void removeServer(String name) {
    try {
      Statement statement = connection.createStatement();
      // query if user not exists
      String query = "SELECT * FROM servers WHERE name = '" + name + "'";
      ResultSet resultSet = statement.executeQuery(query);

      if (!resultSet.next()) {
        Dlog.log(Database.class, Options.ERROR, "Server does not exists");
        return;
      }

      query = "DELETE FROM servers WHERE name = '" + name + "'";

      statement.executeUpdate(query);
      statement.close();
      Dlog.log(Database.class, Options.INFO, "Server " + name + " removed successfully.");
    } catch (SQLException e) {
      Dlog.log(Database.class, Options.ERROR, e.getMessage());
    }
  }

  public static void updateServer(String name, String password) {
    try {
      Statement statement = connection.createStatement();
      // query if user not exists
      String query = "SELECT * FROM servers WHERE name = '" + name + "'";
      ResultSet resultSet = statement.executeQuery(query);

      if (!resultSet.next()) {
        Dlog.log(Database.class, Options.ERROR, "Server does not exists");
        return;
      }

      query = "UPDATE servers SET password = '" + password + "' WHERE name = '" + name + "'";

      statement.executeUpdate(query);
      statement.close();
      Dlog.log(Database.class, Options.INFO, "Server " + name + " updated successfully.");
    } catch (SQLException e) {
      Dlog.log(Database.class, Options.ERROR, e.getMessage());
    }
  }

  public static void changeUserName(String email, String oldName, String newName) {
    try {
      Statement statement = connection.createStatement();
      // query if user exists
      String query =
          "SELECT * FROM users WHERE name = '" + oldName + "'" + " AND email = '" + email + "'";
      ResultSet resultSet = statement.executeQuery(query);

      if (!resultSet.next()) {
        Dlog.log(Database.class, Options.ERROR, "Users does not exists");
        return;
      }

      query = "UPDATE users SET name = '" + newName + "' WHERE name = '" + oldName + "'";

      statement.executeUpdate(query);
      statement.close();
      Dlog.log(Database.class, Options.INFO, "Users " + oldName
          + " name changed successfully.");
    } catch (Exception e) {
      Dlog.log(Database.class, Options.ERROR, e.getMessage());
    }
  }

  public static void changeUserEmail(String email, String oldEmail, String newEmail) {
    try {
      Statement statement = connection.createStatement();
      // query if user exists
      String query =
          "SELECT * FROM users WHERE email = '" + oldEmail + "'" + " AND email = '" + email + "'";
      ResultSet resultSet = statement.executeQuery(query);

      if (!resultSet.next()) {
        Dlog.log(Database.class, Options.ERROR, "Users does not exists");
        return;
      }

      query = "UPDATE users SET email = '" + newEmail + "' WHERE email = '" + oldEmail + "'";

      statement.executeUpdate(query);
      statement.close();
      Dlog.log(Database.class, Options.INFO, "Users " + oldEmail
          + " email changed successfully.");
    } catch (Exception e) {
      Dlog.log(Database.class, Options.ERROR, e.getMessage());
    }
  }

  public static boolean login(String email, String password) {
    try {
      if (connection == null) {
        Dlog.log(Database.class, Options.ERROR, "Database connection is null");
        connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
      }
      Statement statement = connection.createStatement();
      // query if user not exists
      String query =
          "SELECT * FROM users WHERE email = '" + email + "'" + " AND password = '" + password
              + "'";
      ResultSet resultSet = statement.executeQuery(query);

      if (!resultSet.next()) {
        Dlog.log(Database.class, Options.ERROR, "Users does not exists");
        return false;
      }

      statement.close();
      Dlog.log(Database.class, Options.INFO, "Users " + email + " logged in successfully.");
      return true;
    } catch (SQLException e) {
      Dlog.log(Database.class, Options.ERROR, e.getMessage());
      return false;
    }
  }

  public static List<br.unifei.imc.data.servers.Server> getServers() {
    List<br.unifei.imc.data.servers.Server> servers = new ArrayList<>();
    try {
      Dlog.log(Database.class, Options.INFO, "Loading servers list.");
      if (connection == null) {
        connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
        Dlog.log(Database.class, Options.INFO, "Connection to database established.");
      }
      Statement statement = connection.createStatement();
      // query if user not exists
      String query = "SELECT * FROM servers";
      ResultSet resultSet = statement.executeQuery(query);

      while (resultSet.next()) {
        String name = resultSet.getString("hostname");
        String url = resultSet.getString("url");
        String ip = resultSet.getString("ip");
        Integer port = Integer.valueOf(resultSet.getString("port"));
        String monitor = resultSet.getString("monitor");
        String lastUpdate = resultSet.getString("lastUpdate");

        br.unifei.imc.data.servers.Server server =
            new br.unifei.imc.data.servers.Server(name, url, ip, port, monitor, lastUpdate);

        servers.add(server);
      }

      statement.close();
      Dlog.log(Database.class, Options.INFO, "Servers list loaded successfully.");
      return servers;
    } catch (SQLException e) {
      Dlog.log(Database.class, Options.ERROR, e.getMessage());
    }
    return null;
  }

  public static Boolean getServer(String hostname) {
    try {
      Statement statement = connection.createStatement();
      // query if user not exists
      String query = "SELECT * FROM servers WHERE hostname = '" + hostname + "'";
      ResultSet resultSet = statement.executeQuery(query);

      if (!resultSet.next()) {
        Dlog.log(Database.class, Options.ERROR, "Server does not exists");
        return false;
      }

      statement.close();
      Dlog.log(Database.class, Options.INFO, "Server " + hostname
          + " loaded successfully.");
      return true;
    } catch (SQLException e) {
      Dlog.log(Database.class, Options.ERROR, e.getMessage());
      return false;
    }
  }

  // get user details by email
  public static String getUser(String email) {
    try {
      Dlog.log(Database.class, Options.INFO, "Loading user " + email + " details.");
      if (email == null) {
        Dlog.log(Database.class, Options.ERROR, "Email is null. Please, login first.");
      }

      if (connection == null) {
        connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
        Dlog.log(Database.class, Options.INFO, "Connection to database established.");
      }

      Statement statement = connection.createStatement();

      // query if user not exists
      String query = "SELECT * FROM users WHERE email = '" + email + "'";
      ResultSet resultSet = statement.executeQuery(query);

      if (!resultSet.next()) {
        Dlog.log(Database.class, Options.ERROR, "Users does not exists");
        return null;
      }

      Dlog.log(Database.class, Options.INFO, "Users " + email + " loaded successfully.");

      ArrayList<String> user = new ArrayList<>();
      user.add(resultSet.getString("name"));
      user.add(resultSet.getString("email"));
      user.add(resultSet.getString("password"));
      user.add(resultSet.getString("type"));

      statement.close();
      return user.toString();

    } catch (SQLException e) {
      Dlog.log(Database.class, Options.ERROR, e.getMessage());
      return null;
    }
  }

  // get all users
  public static List<User> getUsers() {
    List<User> users = new ArrayList<>();
    try {
      Dlog.log(Database.class, Options.INFO, "Loading users list.");
      if (connection == null) {
        connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
        Dlog.log(Database.class, Options.INFO, "Connection to database established.");
      }
      Statement statement = connection.createStatement();
      // query if user not exists
      String query = "SELECT * FROM users";
      ResultSet resultSet = statement.executeQuery(query);

      while (resultSet.next()) {
        String type = resultSet.getString("type");

        String unCapType = type.toLowerCase().substring(0, 1).toUpperCase()
            + type.toLowerCase().substring(1);

        User user = Factory.createUser(Types.valueOf(unCapType));

        user.setName(resultSet.getString("name"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));

        users.add(user);
      }

      statement.close();
      Dlog.log(Database.class, Options.INFO, "Users list loaded successfully.");
      return users;
    } catch (SQLException e) {
      Dlog.log(Database.class, Options.ERROR, e.getMessage());
      return null;
    } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
             InstantiationException | IllegalAccessException e) {
      Dlog.log(Database.class, Options.ERROR, e.getMessage());
    }
    return null;
  }

  public static void createAdministrator() {
    try {
      Statement statement = connection.createStatement();
      // query if user not exists
      String query = "SELECT * FROM users WHERE type = 'ADMINISTRATOR'";
      ResultSet resultSet = statement.executeQuery(query);

      if (resultSet.next()) {
        Dlog.log(Database.class, Options.ERROR, "Administrator already exists");
        return;
      }

      final String name = "Administrator";
      final String email = "admin@localhost";
      final String password = "admin";
      final String type = Types.Administrator.toString();

      query = "INSERT INTO users (name, email, password, type) VALUES ('" + name + "', '" + email
          + "', '" + password + "', '" + type + "')";
      statement.executeUpdate(query);
      statement.close();
      Dlog.log(Database.class, Options.INFO, "Administrator created successfully.");
    } catch (SQLException e) {
      Dlog.log(Database.class, Options.ERROR, e.getMessage());
    }
  }

  public static void updateServerStatus(String hostname, Integer status) {
    try {
      Statement statement = connection.createStatement();
      // query if user not exists
      String query = "SELECT * FROM servers WHERE hostname = '" + hostname + "'";
      ResultSet resultSet = statement.executeQuery(query);

      if (!resultSet.next()) {
        Dlog.log(Database.class, Options.ERROR, "Server does not exists");
        return;
      }

      String lastCheck = String.valueOf(Instant.now());

      query = "UPDATE servers SET lastStatus = " + status + ", lastCheck = '" + lastCheck
          + "' WHERE hostname = '" + hostname + "'";

      statement.executeUpdate(query);
      statement.close();
      Dlog.log(Database.class, Options.INFO, "Server " + hostname
          + " status updated successfully.");
    } catch (SQLException e) {
      Dlog.log(Database.class, Options.ERROR, e.getMessage());
    }
  }
}