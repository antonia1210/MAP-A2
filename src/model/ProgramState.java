package model;

import model.adt.ExecutionStack;
import model.adt.Out;
import model.adt.SymbolTable;
import model.statement.IStatement;
import model.value.IValue;

public class ProgramState {
    private ExecutionStack<IStatement> executionStack;
    private SymbolTable<String, IValue> symbolTable;
    private Out<IValue> out;
    private IStatement originalProgram;

    public ProgramState(ExecutionStack<IStatement> executionStack, SymbolTable<String, IValue> symbolTable, Out<IValue> out, IStatement originalProgram) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.originalProgram = originalProgram;
        executionStack.push(originalProgram);
    }
    public ExecutionStack<IStatement> getExecutionStack() {
        return executionStack;
    }
    public SymbolTable<String, IValue> getSymbolTable() {
        return symbolTable;
    }
    public Out<IValue> getOut() {
        return out;
    }

    @Override
    public String toString() {
        return "ProgramState\n" +
                "executionStack=" + executionStack +
                "\nsymbolTable=" + symbolTable +
                "\nout=" + out;
    }
}
