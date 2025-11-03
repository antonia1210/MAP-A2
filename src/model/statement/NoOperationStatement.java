package model.statement;

import model.ProgramState;
import model.exception.MyException;

public record NoOperationStatement() implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        return programState;
    }

    @Override
    public String toString() {
        return "nop";
    }
}
