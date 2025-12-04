package model.adt;

import java.util.ArrayList;

public interface IOut<T> {
    void add(T value);
    ArrayList<T> getList();
}
