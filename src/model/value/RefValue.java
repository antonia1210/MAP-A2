package model.value;

import model.type.IType;
import model.type.RefType;

public class RefValue implements IValue {
    private final int address;
    private final IType locationType;
    public RefValue(int address, IType locationType){
        this.address = address;
        this.locationType = locationType;
    }
    public int getAddress(){
        return this.address;
    }
    @Override
    public IType getType(){
        return new RefType(locationType);
    }
    public IType getLocationType(){
        return this.locationType;
    }
    @Override
    public IValue deepCopy() {
        return new RefValue(address, locationType);
    }
    @Override
    public boolean equals(Object another) {
        if (another == this) return true;
        if (!(another instanceof RefValue)) return false;
        return this.address == ((RefValue)another).address && this.locationType.equals(((RefValue)another).locationType);
    }
    @Override
    public String toString() {
        return "("  + address + "," + locationType + ')';
    }
}
