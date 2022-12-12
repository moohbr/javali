package br.unifei.imc.data.users;


import br.unifei.imc.infrastructure.database.Database;

public class Viewer extends User implements IViewer {

    IViewer viewer;

    public Viewer(){
        viewer = new LoginPa();
    }
    @Override
    public void updateOwnPassword(String oldPassword, String newPassword) {
        Database.changeUserPassword(this.getEmail(), oldPassword, newPassword);
    }

    @Override
    public void updateOwnName(String oldName, String newName) {
        Database.changeUserName(this.getEmail(), oldName, newName);
    }

    @Override
    public void updateOwnEmail(String oldEmail, String newEmail) {
        Database.changeUserEmail(this.getEmail(), oldEmail, newEmail);
    }
}

