package br.unifei.imc.infrastructure.configuration;


import io.github.cdimascio.dotenv.Dotenv;

public class Configuration {
    private static final Dotenv dotenv = Dotenv.load();

    public static String getEnvironmentVariable(EnvironmentVariables variable) {
        return dotenv.get(variable.toString());
    }

}
