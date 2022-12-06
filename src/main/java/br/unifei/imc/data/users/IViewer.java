package br.unifei.imc.data.users;

public interface IViewer {
    void updateOwnPassword(String oldPassword, String newPassword);
    void updateOwnName(String oldName, String newName);
    void updateOwnEmail(String oldEmail, String newEmail);
}
