package br.unifei.imc.infrastructure.log;

import org.apache.log4j.Logger;

// Singleton
public final class DLog {
    private static DLog instance;

    private DLog() {
        // The following code emulates slow initialization.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static DLog initialize() {
        if (instance == null) {
            instance = new DLog();
        }
        return instance;
    }


    public static void log(Class<?> clazz, Options option, String message) {
        Logger log = Logger.getLogger(clazz.getName());
        switch (option) {
            case INFO -> log.info(message);
            case ALERT -> log.warn(message);
            case ERROR -> log.error(message);
        }
    }

}
