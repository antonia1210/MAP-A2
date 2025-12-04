package model.expression;

import model.adt.IHeap;
import model.adt.ISymbolTable;
import exception.MyException;
import model.value.IValue;

public record ValueExpression(IValue IValue) implements IExpression {
    @Override
    public IValue evaluate(ISymbolTable<String, IValue> symbolTable, IHeap heap) throws MyException {
        return IValue;
    }
    @Override
    public IExpression deepCopy(){
        return new ValueExpression(IValue.deepCopy());
    }
    @Override
    public String toString() {
        return IValue.toString();
    }
}
