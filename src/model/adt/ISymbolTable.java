package model.adt;

import model.exception.MyException;

public interface ISymbolTable<K,V> {
    void put(K key, V value);
    V lookup(K key) throws Exception;
    void update(K key, V value) throws MyException;
    boolean isDefined(K key);
}
