package model.adt;

import exception.MyException;

import java.util.HashMap;
import java.util.Map;

public class FileTable<K,V> implements IFileTable<K,V> {
    private final Map<K,V> table;
    public FileTable() {
        this.table = new HashMap<>();
    }
    @Override
    public void add(K key, V value) throws MyException {
        if (table.containsKey(key)) {
            throw new MyException("File already exists");
        }
        table.put(key, value);
    }
    @Override
    public void remove(K key) throws MyException {
        if (!table.containsKey(key)) {
            throw new MyException("File does not exist");
        }
        table.remove(key);
    }
    @Override
    public boolean isDefined(K key){
        return table.containsKey(key);
    }
    @Override
    public V lookup(K key) throws MyException {
        if (!table.containsKey(key)) {
            throw new MyException("File does not exist");
        }
        return table.get(key);
    }

    @Override
    public String toString() {
        return "FileTable{" +
                "table=" + table +
                '}';
    }

    @Override
    public Map<K,V> getTable(){
        return table;
    }

}
