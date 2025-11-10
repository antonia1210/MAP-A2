package model.adt;

import exception.MyException;

import java.util.Map;

public interface IFileTable<K,V> {
    void add(K key, V value) throws MyException;
    void remove(K key) throws MyException;
    boolean isDefined(K key);
    V lookup(K key) throws MyException;
    Map<K,V> getTable();
}
