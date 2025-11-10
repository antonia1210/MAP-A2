package model.expression;

import model.adt.SymbolTable;
import exception.MyException;
import exception.OperandIsNotInteger;
import exception.UnknownOperator;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;

public record RelationalExpression(IExpression left, IExpression right, String operator) implements IExpression {
    @Override
    public IValue evaluate(SymbolTable<String,IValue> symbolTable) throws MyException{
        IValue leftValue = left.evaluate(symbolTable);
        if(!leftValue.getType().equals(new IntType())){
            throw new OperandIsNotInteger();
        }
        IValue rightValue = right.evaluate(symbolTable);
        if(!rightValue.getType().equals(new IntType())){
            throw new OperandIsNotInteger();
        }
        int n1 = ((IntValue) leftValue).getValue();
        int n2 = ((IntValue) rightValue).getValue();
        return switch (operator){
            case "<" -> new BoolValue(n1 < n2);
            case ">" -> new BoolValue(n1 > n2);
            case "==" -> new BoolValue(n1 == n2);
            case "!=" -> new BoolValue(n1 != n2);
            case "<=" -> new BoolValue(n1 <= n2);
            case ">=" -> new BoolValue(n1 >= n2);
            default -> throw new UnknownOperator();
        };
    }
    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(left.deepCopy(), right.deepCopy(), operator);
    }
    @Override
    public String toString(){
        return left.toString() + " " + operator + " " + right.toString();
    }
}
