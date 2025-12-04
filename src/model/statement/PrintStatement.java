package model.statement;

import model.adt.IOut;
import model.ProgramState;
import model.adt.ISymbolTable;
import exception.MyException;
import model.value.IValue;
import model.expression.IExpression;

public record PrintStatement(IExpression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        ISymbolTable<String, IValue> symbolTable = programState.getSymbolTable();
        IOut<IValue> out = programState.getOut();
        IValue val = expression.evaluate(symbolTable, programState.getHeap());
        out.add(val);
        return null;
    }
    @Override
    public IStatement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }
    @Override
    public String toString(){
        return "print("+ expression.toString()+")";
    }
}
