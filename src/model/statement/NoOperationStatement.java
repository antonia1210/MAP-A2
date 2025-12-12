package model.statement;

import model.ProgramState;
import exception.MyException;
import model.adt.ISymbolTable;
import model.type.IType;

public record NoOperationStatement() implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        return null;
    }
    @Override
    public IStatement deepCopy() {
        return new NoOperationStatement();
    }
    @Override
    public String toString() {
        return "nop";
    }
    @Override
    public ISymbolTable<String, IType> typeCheck(ISymbolTable<String, IType> typeTable) throws MyException {
        return typeTable;
    }
}
