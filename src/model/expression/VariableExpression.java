package model.expression;

import model.adt.SymbolTable;
import model.exception.MyException;
import model.exception.VariableIsNotDefined;
import model.value.IValue;

public record VariableExpression(String id) implements IExpression {
    @Override
    public IValue evaluate(SymbolTable<String, IValue> symbolTable) throws MyException {
        if(!symbolTable.isDefined(id)){
            throw new VariableIsNotDefined(id);
        }
        return symbolTable.lookup(id);
    }
    @Override
    public String toString(){
        return id;
    }
}
