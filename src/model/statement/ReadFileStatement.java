package model.statement;

import exception.*;
import model.ProgramState;
import model.adt.FileTable;
import model.adt.SymbolTable;
import model.expression.IExpression;
import model.type.IntType;
import model.type.StringType;
import model.value.IValue;
import model.value.IntValue;

import java.io.BufferedReader;
import java.io.IOException;

public record ReadFileStatement(IExpression expression, String variable_name) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable<String, IValue> symbolTable = state.getSymbolTable();
        FileTable<IValue, BufferedReader> fileTable = state.getFileTable();
        if(!symbolTable.isDefined(variable_name)){
            throw new VariableIsNotDefined(variable_name);
        }
        if(!symbolTable.lookup(variable_name).getType().equals(new IntType())){
            throw new OperandIsNotInteger();
        }
        IValue expressionValue = expression.evaluate(symbolTable);
        if(!(expressionValue.getType().equals(new StringType()))){
            throw new ExpressionIsNotString();
        }
        if(!fileTable.isDefined(expressionValue)){
            throw new FileDoesNotExist(expressionValue.toString());
        }
        BufferedReader reader =  fileTable.lookup(expressionValue);
        int intValue;
        try{
            String line = reader.readLine();
            if(line == null){
                intValue = 0;
            }
            else{
                intValue = Integer.parseInt(line.trim());
            }
        }catch (IOException e){
            throw new MyException(e.getMessage());
        }catch (NumberFormatException e){
            throw new MyException(e.getMessage());
        }
        symbolTable.update(variable_name,new IntValue(intValue));
        return state;
    }
    @Override
    public IStatement deepCopy() {
        return new ReadFileStatement(expression.deepCopy(), variable_name);
    }
    @Override
    public String toString(){
        return "Reading from file " + expression.toString() + " " + variable_name;
    }

}
