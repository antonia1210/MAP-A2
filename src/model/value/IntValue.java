package model.value;

import model.type.IntType;
import model.type.IType;

public class IntValue implements IValue {
    private final int value;
    public IntValue(int v){value=v;}
    public int getValue(){return value;}
    @Override
    public IType getType(){return new IntType();}
    @Override
    public String toString(){
        return Integer.toString(value);
    }
    @Override
    public boolean equals(Object another) {
        return another instanceof IntValue iv && value == iv.value;
    }
    @Override
    public IValue deepCopy() {
        return new IntValue(value);
    }
}
