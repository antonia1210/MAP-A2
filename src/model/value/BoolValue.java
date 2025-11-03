package model.value;

import model.type.BoolType;
import model.type.IType;

public class BoolValue implements IValue {
    public boolean value;
    public BoolValue(boolean value){
        this.value = value;
    }
    public boolean getValue(){return value;}
    @Override
    public IType getType(){return new BoolType();}
    @Override
    public String toString(){
        return Boolean.toString(value);
    }
}
