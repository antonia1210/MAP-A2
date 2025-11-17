package model.expression;

import model.adt.Heap;
import model.adt.SymbolTable;
import exception.MyException;
import model.value.IValue;

public interface IExpression {
    IValue evaluate(SymbolTable<String, IValue> symbolTable, Heap heap) throws MyException;
    IExpression deepCopy();
}
