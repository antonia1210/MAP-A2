package model;

import model.adt.ExecutionStack;
import model.adt.FileTable;
import model.adt.Out;
import model.adt.SymbolTable;
import model.statement.IStatement;
import model.value.IValue;

import java.io.BufferedReader;

public class ProgramState {
    private ExecutionStack<IStatement> executionStack;
    private SymbolTable<String, IValue> symbolTable;
    private Out<IValue> out;
    private FileTable<IValue, BufferedReader> fileTable;
    private IStatement originalProgram;

    public ProgramState(FileTable<IValue, BufferedReader> fileTable, ExecutionStack<IStatement> executionStack, SymbolTable<String, IValue> symbolTable, Out<IValue> out, IStatement originalProgram) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = originalProgram.deepCopy();
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
    public FileTable<IValue, BufferedReader> getFileTable() {
        return fileTable;
    }
    @Override
    public String toString() {
        return "ProgramState\n" +
                "executionStack=" + executionStack +
                "\nsymbolTable=" + symbolTable +
                "\nout=" + out +
                "\nfileTable=" + fileTable;
    }
}
