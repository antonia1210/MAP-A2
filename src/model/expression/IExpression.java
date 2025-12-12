package model.expression;
import model.adt.IHeap;
import model.adt.ISymbolTable;
import exception.MyException;
import model.type.IType;
import model.value.IValue;

public interface IExpression {
    IValue evaluate(ISymbolTable<String, IValue> symbolTable, IHeap heap) throws MyException;
    IExpression deepCopy();
    IType typeCheck(ISymbolTable<String, IType> typeTable) throws MyException;
}
