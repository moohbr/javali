package br.unifei.imc.utils.factory;

import br.unifei.imc.bin.cli.Command;
import br.unifei.imc.data.users.Types;
import br.unifei.imc.data.users.User;
import org.jetbrains.annotations.NotNull;

import br.unifei.imc.bin.cli.CommandsAvailable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Factory {
    public static User createUser(@NotNull Types type) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName("br.unifei.imc.data.users." + type);
        Constructor<?> constructor = clazz.getConstructor();
        return (User) constructor.newInstance();
    }

    public static Command getCommand(@NotNull CommandsAvailable command) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String commandPackage = command.toString().toLowerCase();
        Class<?> clazz = Class.forName("br.unifei.imc.bin.cli." + commandPackage + "." + command);
        Constructor<?> constructor = clazz.getConstructor();
        return (Command) constructor.newInstance();
    }

}

