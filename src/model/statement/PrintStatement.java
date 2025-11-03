package model.statement;

import model.adt.Out;
import model.ProgramState;
import model.adt.SymbolTable;
import model.exception.MyException;
import model.value.IValue;
import model.expression.IExpression;

public record PrintStatement(IExpression IExpression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        SymbolTable<String, IValue> symbolTable = programState.getSymbolTable();
        Out<IValue> out = programState.getOut();
        IValue val = IExpression.evaluate(symbolTable);
        out.add(val);
        return programState;
    }
    @Override
    public String toString(){
        return "print("+ IExpression.toString()+")";
    }
}
