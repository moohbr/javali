package br.unifei.imc.infrastructure.log;

import org.apache.log4j.Logger;

// Singleton
public final class Dlog {

  private Dlog() {
    // The following code emulates slow initialization.
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }

  public static void log(Class<?> clazz, Options option, String message) {
    Logger log = Logger.getLogger(clazz.getName());
    switch (option) {
      case INFO -> log.info(message);
      case ALERT -> log.warn(message);
      case ERROR -> log.error(message);
      default -> log.error("Invalid option");
    }
  }

}
