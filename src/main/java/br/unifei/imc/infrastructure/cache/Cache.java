package br.unifei.imc.infrastructure.cache;

import br.unifei.imc.infrastructure.log.Dlog;
import br.unifei.imc.infrastructure.log.Options;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
// Reading a .json as a HashMap

public final class Cache {

  private static final String CACHE_FILE = "cache.json";
  private static final Gson gson = new Gson();

  private Cache() {
    // The following code emulates slow initialization.
    try {
      Thread.sleep(1000);
    } catch (InterruptedException ex) {
      ex.printStackTrace();
    }
  }  private static HashMap<String, String> cache = load();

  private static HashMap<String, String> load() {
    try {
      if (cache == null) {
        cache = new HashMap<>();
        return cache;
      }
      cache = gson.fromJson(new FileReader(CACHE_FILE), (Type) HashMap.class);
      Dlog.log(Cache.class, Options.INFO, "Cache loaded");
      return cache;
    } catch (IOException e) {
      Dlog.log(Cache.class, Options.ERROR, "Error while loading cache");
    }
    return new HashMap<>();
  }

  private static void save() {
    try {
      FileWriter writer = new FileWriter(CACHE_FILE);
      gson.toJson(cache, writer);
      writer.close();
    } catch (IOException e) {
      Dlog.log(Cache.class, Options.ERROR, "Error while saving cache");
    }
  }

  public static void put(String key, String value) {
    load();
    Dlog.log(Cache.class, Options.INFO, "Putting " + key + " in cache");
    cache.put(key, value);
    Dlog.log(Cache.class, Options.INFO, "Saved " + key + " in cache");
    save();
  }

  public static String get(String key) {
    load();
    Dlog.log(Cache.class, Options.INFO, "Getting " + key + " from cache");
    return cache.get(key);
  }

  public static void remove(String key) {
    load();
    Dlog.log(Cache.class, Options.INFO, "Removing " + key + " from cache");
    cache.remove(key);
    Dlog.log(Cache.class, Options.INFO, "Removed " + key + " from cache");
    save();
  }


}