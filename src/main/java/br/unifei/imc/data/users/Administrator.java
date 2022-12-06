package br.unifei.imc.data.users;

public class Administrator extends User implements IAdministrator, IViewer {
    IViewer viewer;

    public Administrator(){
        viewer = new LoginPa();
    }

    public void changePass(String oldPass, String newPass) {
        viewer.updateOwnPassword(oldPass, newPass);
    }

    @Override
    public void addNewUser(String name, String password) {

    }

    @Override
    public void removeUser(String name) {
    }


    @Override
    public void changeUserPassword(String name, String oldPassword, String newPassword) {
    }

    @Override
    public void addNewServer(String name, String password) {
    }

    @Override
    public void removeServer(String name) {
    }

    @Override
    public void updateServer(String name, String password) {
    }

    @Override
    public void updateOwnPassword(String oldPassword, String newPassword) {
    }

    @Override
    public void updateOwnName(String oldName, String newName) {
    }

    @Override
    public void updateOwnEmail(String oldEmail, String newEmail) {
    }
}
