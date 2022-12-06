package br.unifei.imc.data.users;

import lombok.Getter;
import lombok.Setter;


// All user has a name, email, password, type, last login and last notification time
public class User {
    @Getter @Setter private String name;
    @Getter @Setter private String password;

    @Getter @Setter private String email;

    @Getter @Setter private String type;

    @Getter @Setter private long lastLogin;

    @Getter @Setter private long lastNotification;

}
