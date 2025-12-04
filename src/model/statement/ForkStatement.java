package model.statement;

import exception.MyException;
import model.ProgramState;
import model.adt.ExecutionStack;
import model.adt.ISymbolTable;
import model.adt.SymbolTable;
import model.value.IValue;

public record ForkStatement(IStatement statement) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyException{
        ISymbolTable<String, IValue> newSymbolTable = programState.getSymbolTable().deepCopy();
        ExecutionStack<IStatement> newExecutionStack = new ExecutionStack<>();
        newExecutionStack.push(statement.deepCopy());
        return new ProgramState(programState.getHeap(), programState.getFileTable(), newExecutionStack, newSymbolTable, programState.getOut(), null);
    }
    @Override
    public IStatement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }
    @Override
    public String toString() {
        return "fork " + statement;
    }
}
