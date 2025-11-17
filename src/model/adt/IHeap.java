package model.adt;

import model.value.IValue;

import java.util.Map;

public interface IHeap{
    int allocate(IValue value);
    IValue get(int address);
    void put(int address, IValue value);
    boolean isDefined(int address);
    Map<Integer,IValue> getAll();
    void setContent(Map<Integer,IValue> newHeap);
    String toString();
}
