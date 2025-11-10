package model.statement;

import model.adt.Out;
import model.ProgramState;
import model.adt.SymbolTable;
import exception.MyException;
import model.value.IValue;
import model.expression.IExpression;

public record PrintStatement(IExpression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        SymbolTable<String, IValue> symbolTable = programState.getSymbolTable();
        Out<IValue> out = programState.getOut();
        IValue val = expression.evaluate(symbolTable);
        out.add(val);
        return programState;
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
