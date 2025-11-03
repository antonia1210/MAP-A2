package model.expression;

import model.adt.SymbolTable;
import model.exception.DivisionByZero;
import model.exception.MyException;
import model.exception.OperandIsNotInteger;
import model.exception.UnknownOperator;
import model.type.IntType;
import model.value.IntValue;
import model.value.IValue;

public record ArithmeticExpression(IExpression e1, IExpression e2, char operation) implements IExpression {
    @Override
    public IValue evaluate(SymbolTable<String, IValue> symbolTable) throws MyException {
        IValue v1 = e1.evaluate(symbolTable);
        if(!v1.getType().equals(new IntType())){
            throw new OperandIsNotInteger();
        }
        IValue v2 = e2.evaluate(symbolTable);
        if(!v2.getType().equals(new IntType())){
            throw new OperandIsNotInteger();
        }
        int n1 = ((IntValue)v1).getValue();
        int n2 = ((IntValue)v2).getValue();
        return switch(operation){
            case '+' -> new IntValue(n1+n2);
            case '-' -> new IntValue(n1-n2);
            case '*' -> new IntValue(n1*n2);
            case '/' -> {
                if(n2 == 0) throw new DivisionByZero();
                yield new IntValue(n1/n2);
            }
            default -> throw new UnknownOperator();
        };
    }
    @Override
    public String toString() {
        return e1 + " " +  operation + " " + e2;
    }
}
