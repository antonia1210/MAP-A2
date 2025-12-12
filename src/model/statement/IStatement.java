package model.statement;

import model.ProgramState;
import exception.MyException;
import model.adt.ISymbolTable;
import model.type.IType;

public interface IStatement {
    ProgramState execute(ProgramState programState) throws MyException;
    IStatement deepCopy();
    ISymbolTable<String, IType> typeCheck(ISymbolTable<String, IType> typeTable) throws MyException;
}
