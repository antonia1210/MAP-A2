package model.statement;

import model.ProgramState;
import exception.MyException;

public record NoOperationStatement() implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        return programState;
    }
    @Override
    public IStatement deepCopy() {
        return new NoOperationStatement();
    }
    @Override
    public String toString() {
        return "nop";
    }
}
