package model;

import exception.MyException;
import exception.StackIsEmpty;
import model.adt.*;
import model.statement.IStatement;
import model.value.IValue;

import java.io.BufferedReader;

public class ProgramState {
    private IExecutionStack<IStatement> executionStack;
    private ISymbolTable<String, IValue> symbolTable;
    private IOut<IValue> out;
    private IFileTable<IValue, BufferedReader> fileTable;
    private IHeap heap;
    private IStatement originalProgram;
    private int id;
    private static int lastId = 0;

    private static synchronized int nextId(){
        lastId++;
        return lastId;
    }

    public ProgramState(IHeap heap, IFileTable<IValue, BufferedReader> fileTable, IExecutionStack<IStatement> executionStack, ISymbolTable<String, IValue> symbolTable, IOut<IValue> out, IStatement originalProgram) {
        this.id = nextId();
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.out = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.originalProgram = originalProgram != null ? originalProgram.deepCopy() : null;
        if (originalProgram != null) {
            executionStack.push(originalProgram);
        }
    }
    public int getId() {return id;}
    public IExecutionStack<IStatement> getExecutionStack() {
        return executionStack;
    }
    public ISymbolTable<String, IValue> getSymbolTable() {
        return symbolTable;
    }
    public IOut<IValue> getOut() {
        return out;
    }
    public IFileTable<IValue, BufferedReader> getFileTable() {
        return fileTable;
    }
    public IHeap getHeap() { return  heap; }
    @Override
    public String toString() {
        return "ProgramState id=" + id + "\n" +
                "executionStack=" + executionStack +
                "\nsymbolTable=" + symbolTable +
                "\nout=" + out +
                "\nfileTable=" + fileTable;
    }
    public Boolean isNotCompleted(){
        if(!executionStack.isEmpty()){
            return true;
        }
        return false;
    }
    public ProgramState oneStep() throws MyException {
        if(executionStack.isEmpty()){
            throw new StackIsEmpty();
        }
        IStatement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

}
