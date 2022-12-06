package br.unifei.imc.data.users;

public interface IAdministrator { // Create things da vida
    void addNewUser(String name, String password);
    void removeUser(String name);

    void changeUserPassword(String name, String oldPassword, String newPassword);

    void addNewServer(String name, String password);
    void removeServer(String name);
    void updateServer(String name, String password);
}
