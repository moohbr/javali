package br.unifei.imc.infrastructure.cache;

import java.util.HashMap;

public class Cache {
    private static Cache instance;

    // Create a hash map for cache
    private HashMap<String, Object> cache = new HashMap<String, Object>();

    private Cache() {

    }

    public static Cache getInstance() {
        if (instance == null) {
            instance = new Cache();
        }
        return instance;
    }

    public void add(String key, Object value) {
        cache.put(key, value);
    }

    public Object get(String key) {
        return cache.get(key);
    }

    public void remove(String key) {
        cache.remove(key);
    }

}
