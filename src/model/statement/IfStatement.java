package model.statement;

import model.adt.IExecutionStack;
import model.ProgramState;
import model.adt.IHeap;
import model.adt.ISymbolTable;
import exception.MyException;
import exception.UnknownOperator;
import model.expression.IExpression;
import model.type.BoolType;
import model.value.BoolValue;
import model.value.IValue;

public record IfStatement(IExpression condition, IStatement thenStatement, IStatement elseStatement) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        ISymbolTable<String, IValue> symbolTable = programState.getSymbolTable();
        IExecutionStack<IStatement> executionStack = programState.getExecutionStack();
        IHeap heap = programState.getHeap();
        IValue conditionIValue = condition.evaluate(symbolTable, heap);
        if(!conditionIValue.getType().equals(new BoolType())) {
            throw new UnknownOperator();
        }
        if(((BoolValue) conditionIValue).getValue()){
            executionStack.push(thenStatement);
        }
        else{
            executionStack.push(elseStatement);
        }
        return programState;
    }
    @Override
    public IStatement deepCopy() {
        return new IfStatement(condition.deepCopy(), thenStatement.deepCopy(), elseStatement);
    }
    @Override
    public String toString() {
        return "if(" + condition + ") then(" + thenStatement + ") else(" + elseStatement + ")";
    }
}
