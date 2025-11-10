package model.type;

import model.value.IValue;

public interface IType {
    public boolean equals(Object obj);
    IValue defaultValue();
    IType deepCopy();
}
