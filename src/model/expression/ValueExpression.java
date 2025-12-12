package model.expression;

import model.adt.IHeap;
import model.adt.ISymbolTable;
import exception.MyException;
import model.type.IType;
import model.value.IValue;

public record ValueExpression(IValue value) implements IExpression {
    @Override
    public IValue evaluate(ISymbolTable<String, IValue> symbolTable, IHeap heap) throws MyException {
        return value;
    }
    @Override
    public IExpression deepCopy(){
        return new ValueExpression(value.deepCopy());
    }
    @Override
    public String toString() {
        return value.toString();
    }
    @Override
    public IType typeCheck(ISymbolTable<String, IType> typeTable) throws MyException{
        return value.getType();
    }
}
