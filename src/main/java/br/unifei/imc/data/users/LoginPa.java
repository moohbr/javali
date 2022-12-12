package br.unifei.imc.data.users;

public class LoginPa implements Iviewer {

  @Override
  public void updateOwnPassword(String oldPassword, String newPassword) {
    System.out.println("updateOwnPassword");
  }

  @Override
  public void updateOwnName(String oldName, String newName) {

  }

  @Override
  public void updateOwnEmail(String oldEmail, String newEmail) {

  }
}
