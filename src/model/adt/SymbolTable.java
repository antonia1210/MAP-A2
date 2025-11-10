package model.adt;

import exception.MyException;
import exception.VariableIsNotDefined;

import java.util.HashMap;

public class SymbolTable<K,V> implements ISymbolTable<K,V> {
    private HashMap<K,V> dictionary = new HashMap<K,V>();
    @Override
    public void put(K key, V value) {
        dictionary.put(key, value);
    }
    @Override
    public V lookup(K key) throws MyException {
        if(!dictionary.containsKey(key)) throw new VariableIsNotDefined(key.toString());
        return dictionary.get(key);
    }
    @Override
    public void update(K key, V value) throws MyException {
        if(!dictionary.containsKey(key)) throw new VariableIsNotDefined(key.toString());
        dictionary.put(key, value);
    }
    @Override
    public boolean isDefined(K key) {
        return dictionary.containsKey(key);
    }
    @Override
    public String  toString() {
        return dictionary.toString();
    }
    public HashMap<K,V> getAll() {return dictionary;}
}
