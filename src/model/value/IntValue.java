package model.value;

import model.type.IntType;
import model.type.IType;

public class IntValue implements IValue {
    private int value;
    public IntValue(int v){value=v;}
    public int getValue(){return value;}
    @Override
    public IType getType(){return new IntType();}
    @Override
    public String toString(){
        return Integer.toString(value);
    }
}
