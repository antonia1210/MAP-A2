package model.statement;

import exception.ExpressionIsNotString;
import exception.FileAlreadyOpened;
import exception.FileDoesNotExist;
import model.ProgramState;
import model.adt.FileTable;
import exception.MyException;
import model.expression.IExpression;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public record OpenRFileStatement(IExpression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        FileTable<IValue, BufferedReader> fileTable = programState.getFileTable();
        IValue value = expression.evaluate(programState.getSymbolTable());
        if (!value.getType().equals(new StringType())){
            throw new ExpressionIsNotString();
        }
        String filename = ((StringValue) value).getValue();
        if(fileTable.isDefined(value)){
            throw new FileAlreadyOpened(filename);
        }
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            fileTable.add(value,bufferedReader);
        }
        catch(IOException e){
            throw new FileDoesNotExist(filename);
        }
        return programState;
    }
    @Override
    public IStatement deepCopy() {
        return new OpenRFileStatement(expression.deepCopy());
    }
    @Override
    public String toString(){
        return "Open file " + expression.toString();
    }
}
