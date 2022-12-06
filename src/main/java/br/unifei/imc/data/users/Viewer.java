package br.unifei.imc.data.users;

public class Viewer extends User implements IViewer {

    IViewer viewer;

    public Viewer(){
        viewer = new LoginPa();
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

