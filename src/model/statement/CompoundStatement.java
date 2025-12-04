package model.statement;

import model.adt.IExecutionStack;
import model.ProgramState;
import exception.MyException;

public record CompoundStatement(IStatement first, IStatement second) implements IStatement {
    @Override
    public String toString() {
        return "(" + first.toString() + "; " + second.toString() + ")";
    }
    @Override
    public IStatement deepCopy() {
        return new CompoundStatement(first.deepCopy(), second.deepCopy());
    }
    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        IExecutionStack<IStatement> executionStack = programState.getExecutionStack();
        executionStack.push(second);
        executionStack.push(first);
        return null;
    }
}
