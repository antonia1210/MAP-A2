package model.type;

public class IntType implements IType {
    @Override
    public boolean equals(Object other){
        return other instanceof IntType;
    }
    @Override
    public String toString() {
        return "int";
    }
}
