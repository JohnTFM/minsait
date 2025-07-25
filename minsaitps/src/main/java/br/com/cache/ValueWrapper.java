package br.com.cache;

public class ValueWrapper<V> {

    private final V value;

    private long lastUsed;

    public ValueWrapper(V value) {
        this.value = value;
        lastUsed = System.nanoTime();
    }

    public V getValue() {
        lastUsed = System.nanoTime();
        return value;
    }

    public long getLastUsed() {
        return lastUsed;
    }
}
