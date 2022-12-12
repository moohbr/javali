package br.unifei.imc.data.users;

public interface Iadministrator { // Create things da vida

  void addNewUser(String name, String email, String password, String role);

  void removeUser(String name);

  void changeUserPassword(String name, String oldPassword, String newPassword);

  void addNewServer(String hostname, String url, String ip, Integer port, Boolean monitor);

  void removeServer(String name);

  void updateServer(String name, String password);
}