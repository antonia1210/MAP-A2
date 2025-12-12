package model.expression;

import model.adt.IHeap;
import model.adt.ISymbolTable;
import exception.MyException;
import exception.VariableIsNotDefined;
import model.type.IType;
import model.value.IValue;

public record VariableExpression(String id) implements IExpression {
    @Override
    public IValue evaluate(ISymbolTable<String, IValue> symbolTable, IHeap heap) throws MyException {
        if(!symbolTable.isDefined(id)){
            throw new VariableIsNotDefined(id);
        }
        return symbolTable.lookup(id);
    }
    @Override
    public IExpression deepCopy(){
        return new VariableExpression(id);
    }
    @Override
    public String toString(){
        return id;
    }
    @Override
    public IType typeCheck(ISymbolTable<String, IType> typeTable) throws MyException{
        return typeTable.lookup(id);
    }
}
