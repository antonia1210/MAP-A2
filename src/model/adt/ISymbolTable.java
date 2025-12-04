package model.adt;

import exception.MyException;

import java.util.HashMap;

public interface ISymbolTable<K,V> {
    void put(K key, V value);
    V lookup(K key) throws MyException;
    void update(K key, V value) throws MyException;
    boolean isDefined(K key);
    ISymbolTable<K,V> deepCopy();
    HashMap<K,V> getAll();
}
