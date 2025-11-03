package model.expression;

import model.adt.SymbolTable;
import model.exception.MyException;
import model.value.IValue;

public record ValueExpression(IValue IValue) implements IExpression {
    @Override
    public IValue evaluate(SymbolTable<String, IValue> symbolTable) throws MyException {
        return IValue;
    }
    @Override
    public String toString() {
        return IValue.toString();
    }
}
