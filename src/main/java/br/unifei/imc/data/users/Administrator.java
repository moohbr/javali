package br.unifei.imc.data.users;

import br.unifei.imc.infrastructure.database.Database;

public class Administrator extends User implements IAdministrator, IViewer {
    IViewer viewer;

    public Administrator(){
        viewer = new LoginPa();
    }

    @Override
    public void addNewUser(String name, String email, String password, String role) {
        Database.addUser(name, email, password, role);
    }

    @Override
    public void removeUser(String email) {
        Database.removeUser(email);
    }


    @Override
    public void changeUserPassword(String name, String oldPassword, String newPassword) {
        Database.changeUserPassword(name, oldPassword, newPassword);
    }

    @Override
    public void addNewServer(String hostname, String url, String ip, Integer port, Boolean monitor) {
        Database.addServer(hostname, url, ip, port, monitor);
    }

    @Override
    public void removeServer(String name) {
        Database.removeServer(name);
    }

    @Override
    public void updateServer(String name, String password) {
        Database.updateServer(name, password);
    }

    @Override
    public void updateOwnPassword(String oldPassword, String newPassword) {
        viewer.updateOwnPassword(oldPassword, newPassword);
    }

    @Override
    public void updateOwnName(String oldName, String newName) {
        viewer.updateOwnName(oldName, newName);
    }

    @Override
    public void updateOwnEmail(String oldEmail, String newEmail) {
        viewer.updateOwnEmail(oldEmail, newEmail);
    }
}
