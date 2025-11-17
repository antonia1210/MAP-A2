package model.expression;

import exception.InvalidHeapAddress;
import exception.MyException;
import exception.VariableNotRefType;
import model.adt.Heap;
import model.adt.SymbolTable;
import model.value.IValue;
import model.value.RefValue;

public record ReadHeapExpression(IExpression expression) implements IExpression {
    @Override
    public IValue evaluate(SymbolTable<String,IValue> symbolTable, Heap heap) throws MyException{
        IValue value = expression.evaluate(symbolTable, heap);
        if(!(value instanceof RefValue))
            throw new VariableNotRefType(value.toString());
        RefValue refValue = (RefValue)value;
        int address = refValue.getAddress();
        if(!heap.isDefined(address)){
            throw new InvalidHeapAddress(address);
        }
        return heap.get(address);
    }
    @Override
    public IExpression deepCopy() {
        return new ReadHeapExpression(expression.deepCopy());
    }
    @Override
    public String toString() {
        return "Read Heap " + expression;
    }
}
