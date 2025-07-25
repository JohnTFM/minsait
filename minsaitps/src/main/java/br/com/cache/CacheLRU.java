package br.com.cache;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CacheLRU<K,V> implements Cache<K,V>{

    private final HashMap<K, ValueWrapper<V> > data;

    private final int maxSize;

    public CacheLRU(int i) {

        data = new LinkedHashMap<>(i);

        maxSize = i;

    }

    @Override
    public void put(K key, V value) {

        synchronized (this){

            long dataSize = data.size();

            if(dataSize==maxSize && !data.containsKey(key)){

                long oldestTimestamp = Long.MAX_VALUE;

                K keyFromOldestTmstmp = null;

                for (Map.Entry<K, ValueWrapper<V>> entry : data.entrySet()) {

                    if(entry.getValue().getLastUsed() < oldestTimestamp){

                        oldestTimestamp = entry.getValue().getLastUsed();

                        keyFromOldestTmstmp = entry.getKey();

                    }

                }

                data.remove(keyFromOldestTmstmp);

            }

            data.put(key, new ValueWrapper<>(value));

        }

    }

    @Override
    public V get(K key) {

        ValueWrapper<V> value = data.get(key);

        if(value == null){
            return null;
        }

        return data.get(key).getValue();

    }

    @Override
    public synchronized void remove(K key) {
        data.remove(key);
    }

    @Override
    public long size() {
        return data.size();
    }
}
