package br.unifei.imc.data.users;

/**
 * Viewer class.
 *
 * @author @moohbr
 *
 * @version 1.0
 * @since 1.0
 * @see br.unifei.imc.infrastructure.database.Database
 */
public interface Iadministrator {

  void addNewUser(String name, String email, String password, String role);

  void removeUser(String name);

  void changeUserPassword(String name, String oldPassword, String newPassword);

  void addNewServer(String hostname, String url, String ip, Integer port, Boolean monitor);

  void removeServer(String name);

  void updateServer(String name, String password);
}
