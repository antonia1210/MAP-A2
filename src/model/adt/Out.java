package model.adt;

import java.util.ArrayList;

public class Out<T> implements IOut<T> {
    private ArrayList<T> list = new ArrayList<>();
    @Override
    public void add(T value) {
        list.add(value);
    }
    @Override
    public String toString() {
        return list.toString();
    }
    public ArrayList<T> getList(){return list;}
}
