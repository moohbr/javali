package br.unifei.imc.bin.cli.init;

import br.unifei.imc.bin.cli.Command;
import br.unifei.imc.infrastructure.log.DLog;
import br.unifei.imc.infrastructure.log.Options;
import br.unifei.imc.infrastructure.database.Database;

import picocli.CommandLine;

@CommandLine.Command(name = "init", mixinStandardHelpOptions = true, version = "1.0", description = "Init the project")

public class Init extends Command implements Runnable {
    public void run() {
        DLog.log(getClass(), Options.INFO, "Initializing the database");
        try{
            Database.initialize();
        } catch (Exception e) {
            DLog.log(getClass(), Options.ERROR, "Error initializing the database");
            DLog.log(getClass(), Options.ERROR, e.getMessage());
        }
    }
}

