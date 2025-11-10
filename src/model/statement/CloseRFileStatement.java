package model.statement;

import exception.ExpressionIsNotString;
import exception.FileDoesNotExist;
import model.ProgramState;
import model.adt.FileTable;
import model.adt.SymbolTable;
import exception.MyException;
import model.expression.IExpression;
import model.type.StringType;
import model.value.IValue;
import java.io.BufferedReader;
import java.io.IOException;

public record CloseRFileStatement(IExpression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        FileTable<IValue, BufferedReader> fileTable = state.getFileTable();
        SymbolTable<String, IValue> symbolTable = state.getSymbolTable();
        IValue value = expression.evaluate(symbolTable);
        if(!value.getType().equals(new StringType())){
            throw new ExpressionIsNotString();
        }
        if(!fileTable.isDefined(value)){
            throw new FileDoesNotExist(value.toString());
        }
        BufferedReader reader = fileTable.lookup(value);
        try{
            reader.close();
        }catch(IOException e){
            throw new MyException(e.getMessage());
        }
        fileTable.remove(value);
        return state;
    }
    @Override
    public IStatement deepCopy() {
        return new CloseRFileStatement(expression.deepCopy());
    }
    @Override
    public String toString(){
        return "Close File " +  expression.toString();
    }

}
