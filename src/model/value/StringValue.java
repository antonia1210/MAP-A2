package model.value;

import model.type.IType;
import model.type.StringType;

public class StringValue implements IValue {
    private final String value;
    public StringValue(String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return value;
    }
    @Override
    public IType getType() {
        return new StringType();
    }
    public String getValue() {
        return value;
    }
    @Override
    public boolean equals(Object another) {
        return another instanceof StringValue sv && sv.value.equals(value);
    }
    @Override
    public IValue deepCopy() {
        return new StringValue(value);
    }
}
