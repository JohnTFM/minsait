package br.com.cache;

/**
 * @param <K> Tipo da chave
 * @param <V> Tipo do valor
 */
public interface  Cache<K,V> {

    void put(K key, V value);

    V get(K key);

    void remove(K key);

    long size();

}
