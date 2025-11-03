package model.expression;

import model.adt.SymbolTable;
import model.exception.MyException;
import model.value.IValue;

public interface IExpression {
    IValue evaluate(SymbolTable<String, IValue> symbolTable) throws MyException;
}
