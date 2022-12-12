package br.unifei.imc.infrastructure.database;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.unifei.imc.data.DataTypes;

import br.unifei.imc.infrastructure.configuration.Configuration;
import br.unifei.imc.infrastructure.configuration.EnvironmentVariables;

import br.unifei.imc.infrastructure.log.DLog;
import br.unifei.imc.infrastructure.log.Options;


// Implements the singleton pattern
public class Database {
    private static Database instance;
    private static Connection connection;

    private Database() {
        try {
            Class.forName("org.sqlite.JDBC");

            String databaseName = Configuration.getEnvironmentVariable(EnvironmentVariables.SQL_FILE_NAME);

            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);

            DLog.log(Database.class, Options.INFO, "Database connection established.");
            Thread.sleep(1000);

        } catch (ClassNotFoundException | SQLException | InterruptedException e) {
            DLog.log(Database.class, Options.ERROR, e.getMessage());
        }
    }

    public static void initialize() {
        if (instance == null) {
            instance = new Database();
            initializeTables();
        }
    }

    public static void initializeTables(){
        DLog.log(Database.class, Options.INFO, "Initializing tables...");
        try {
            Statement statement = connection.createStatement();

            String dataPackage = "br.unifei.imc.data";

            for (DataTypes type : DataTypes.values()) {
                String tableName = type.toString();
                String className = dataPackage + "." + tableName + "." + tableName.substring(0, 1).toUpperCase() + tableName.substring(1);
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
            DLog.log(Database.class, Options.ERROR, e.getMessage());
        }
    }

    public static void addUser(String name, String email, String password, String role) {
        try {
            Statement statement = connection.createStatement();
            // query if user not exists
            String query = "SELECT * FROM users WHERE name = '" + name + "'" + " OR email = '" + email + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                DLog.log(Database.class, Options.ERROR, "User already exists");
                return;
            }

            query = "INSERT INTO users (name, email, password, type) VALUES ('" + name + "', '" + email + "', '" + password + "', '" + role + "')";

            statement.executeUpdate(query);
            statement.close();
            DLog.log(Database.class, Options.INFO, "User " + name + " added successfully.");
        } catch (SQLException e) {
            DLog.log(Database.class, Options.ERROR, e.getMessage());
        }
    }

    public static void removeUser(String email){
        try {
            Statement statement = connection.createStatement();
            // query if user not exists
            String query = "SELECT * FROM users WHERE email = '" + email + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                DLog.log(Database.class, Options.ERROR, "User does not exists");
                return;
            }

            query = "DELETE FROM users WHERE email = '" + email + "'";

            statement.executeUpdate(query);
            statement.close();
            DLog.log(Database.class, Options.INFO, "User " + email + " removed successfully.");
        } catch (SQLException e) {
            DLog.log(Database.class, Options.ERROR, e.getMessage());
        }
    }

    public static void changeUserPassword(String name, String oldPassword, String newPassword) {
        try{
            Statement statement = connection.createStatement();
            // query if user exists
            String query = "SELECT * FROM users WHERE name = '" + name + "'" + " AND password = '" + oldPassword + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                DLog.log(Database.class, Options.ERROR, "User does not exists");
                return;
            }

            query = "UPDATE users SET password = '" + newPassword + "' WHERE name = '" + name + "'";

            statement.executeUpdate(query);
            statement.close();
            DLog.log(Database.class, Options.INFO, "User " + name + " password changed successfully.");
        } catch (Exception e) {
            DLog.log(Database.class, Options.ERROR, e.getMessage());
        }
    }

    public static void addServer(String hostname, String url, String ip, Integer port, Boolean monitor){
        try {
            Statement statement = connection.createStatement();
            // query if user not exists
            String query = "SELECT * FROM servers WHERE hostname = '" + hostname + "'" + " OR url = '" + url + "'" + " OR ip = '" + ip + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                DLog.log(Database.class, Options.ERROR, "Server already exists");
                return;
            }

            query = "INSERT INTO servers (hostname, url, ip, port, monitor) VALUES ('" + hostname + "', '" + url + "', '" + ip + "', '" + port + "', '" + monitor + "')";

            statement.executeUpdate(query);
            statement.close();
            DLog.log(Database.class, Options.INFO, "Server " + hostname + " added successfully.");
        } catch (SQLException e) {
            DLog.log(Database.class, Options.ERROR, e.getMessage());
        }
    }

    public static void removeServer(String name) {
        try {
            Statement statement = connection.createStatement();
            // query if user not exists
            String query = "SELECT * FROM servers WHERE name = '" + name + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                DLog.log(Database.class, Options.ERROR, "Server does not exists");
                return;
            }

            query = "DELETE FROM servers WHERE name = '" + name + "'";

            statement.executeUpdate(query);
            statement.close();
            DLog.log(Database.class, Options.INFO, "Server " + name + " removed successfully.");
        } catch (SQLException e) {
            DLog.log(Database.class, Options.ERROR, e.getMessage());
        }
    }

    public static void updateServer(String name, String password) {
        try {
            Statement statement = connection.createStatement();
            // query if user not exists
            String query = "SELECT * FROM servers WHERE name = '" + name + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                DLog.log(Database.class, Options.ERROR, "Server does not exists");
                return;
            }

            query = "UPDATE servers SET password = '" + password + "' WHERE name = '" + name + "'";

            statement.executeUpdate(query);
            statement.close();
            DLog.log(Database.class, Options.INFO, "Server " + name + " updated successfully.");
        } catch (SQLException e) {
            DLog.log(Database.class, Options.ERROR, e.getMessage());
        }
    }

    public static void changeUserName(String email, String oldName, String newName) {
        try{
            Statement statement = connection.createStatement();
            // query if user exists
            String query = "SELECT * FROM users WHERE name = '" + oldName + "'" + " AND email = '" + email + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                DLog.log(Database.class, Options.ERROR, "User does not exists");
                return;
            }

            query = "UPDATE users SET name = '" + newName + "' WHERE name = '" + oldName + "'";

            statement.executeUpdate(query);
            statement.close();
            DLog.log(Database.class, Options.INFO, "User " + oldName + " name changed successfully.");
        } catch (Exception e) {
            DLog.log(Database.class, Options.ERROR, e.getMessage());
        }
    }

    public static void changeUserEmail(String email, String oldEmail, String newEmail) {
        try{
            Statement statement = connection.createStatement();
            // query if user exists
            String query = "SELECT * FROM users WHERE email = '" + oldEmail + "'" + " AND email = '" + email + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                DLog.log(Database.class, Options.ERROR, "User does not exists");
                return;
            }

            query = "UPDATE users SET email = '" + newEmail + "' WHERE email = '" + oldEmail + "'";

            statement.executeUpdate(query);
            statement.close();
            DLog.log(Database.class, Options.INFO, "User " + oldEmail + " email changed successfully.");
        } catch (Exception e) {
            DLog.log(Database.class, Options.ERROR, e.getMessage());
        }
    }

    public static boolean login(String email, String password){
        try {
            Statement statement = connection.createStatement();
            // query if user not exists
            String query = "SELECT * FROM users WHERE email = '" + email + "'" + " AND password = '" + password + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                DLog.log(Database.class, Options.ERROR, "User does not exists");
                return false;
            }

            statement.close();
            DLog.log(Database.class, Options.INFO, "User " + email + " logged in successfully.");
            return true;
        } catch (SQLException e) {
            DLog.log(Database.class, Options.ERROR, e.getMessage());
            return false;
        }
    }

    public static List<String> getServers(){
        List<String> servers = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            // query if user not exists
            String query = "SELECT * FROM servers";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                servers.add(resultSet.getString("hostname"));
            }

            statement.close();
            DLog.log(Database.class, Options.INFO, "Servers list loaded successfully.");
            return servers;
        } catch (SQLException e) {
            DLog.log(Database.class, Options.ERROR, e.getMessage());
            return null;
        }
    }

    public static Boolean getServer(String hostname) {
        try {
            Statement statement = connection.createStatement();
            // query if user not exists
            String query = "SELECT * FROM servers WHERE hostname = '" + hostname + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                DLog.log(Database.class, Options.ERROR, "Server does not exists");
                return false;
            }

            statement.close();
            DLog.log(Database.class, Options.INFO, "Server " + hostname + " loaded successfully.");
            return true;
        } catch (SQLException e) {
            DLog.log(Database.class, Options.ERROR, e.getMessage());
            return false;
        }
    }
}