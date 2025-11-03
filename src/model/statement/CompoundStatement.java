package model.statement;

import model.adt.ExecutionStack;
import model.ProgramState;
import model.exception.MyException;

public record CompoundStatement(IStatement first, IStatement second) implements IStatement {
    @Override
    public String toString() {
        return "(" + first.toString() + "; " + second.toString() + ")";
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        ExecutionStack<IStatement> executionStack = programState.getExecutionStack();
        executionStack.push(second);
        executionStack.push(first);
        return programState;
    }
}
