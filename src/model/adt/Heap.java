package model.adt;

import exception.InvalidHeapAddress;
import exception.MyException;
import model.value.IValue;

import java.util.HashMap;
import java.util.Map;

public class Heap implements IHeap{
    private Map<Integer, IValue> heap;
    private int freeAddress;
    public Heap(){
        heap = new HashMap<>();
        freeAddress = 1;
    }
    private int getNextFreeAddress(){
        while(heap.containsKey(freeAddress)){
            freeAddress++;
        }
        return freeAddress;
    }
    @Override
    public int allocate(IValue value) {
        int address = getNextFreeAddress();
        heap.put(address, value);
        freeAddress++;
        return address;
    }
    @Override
    public IValue get(int address) {
        return heap.get(address);
    }
    @Override
    public void put(int address, IValue value) throws MyException {
        if(!heap.containsKey(address)){
            throw new InvalidHeapAddress(address);
        }
        heap.put(address, value);
    }
    @Override
    public boolean isDefined(int address) {
        return heap.containsKey(address);
    }
    @Override
    public Map<Integer,IValue> getAll(){
        return heap;
    }
    @Override
    public void setContent(Map<Integer,IValue> newHeap) {
        heap = newHeap;
    }
    @Override
    public String toString() {
        return heap.toString();
    }

}
