package br.unifei.imc.infrastructure.database;

import java.lang.reflect.Field;
import java.sql.*;

import br.unifei.imc.data.users.User;
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

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            DLog.log(Database.class, Options.ERROR, e.getMessage());
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
        } catch (SQLException e) {
            DLog.log(Database.class, Options.ERROR, e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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

}