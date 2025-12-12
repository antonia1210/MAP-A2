package model.expression;

import model.adt.IHeap;
import model.adt.ISymbolTable;
import exception.DivisionByZero;
import exception.MyException;
import exception.OperandIsNotInteger;
import exception.UnknownOperator;
import model.type.IType;
import model.type.IntType;
import model.value.IntValue;
import model.value.IValue;

public record ArithmeticExpression(IExpression e1, IExpression e2, char operation) implements IExpression {
    @Override
    public IValue evaluate(ISymbolTable<String, IValue> symbolTable, IHeap heap) throws MyException {
        IValue v1 = e1.evaluate(symbolTable, heap);
        if(!v1.getType().equals(new IntType())){
            throw new OperandIsNotInteger();
        }
        IValue v2 = e2.evaluate(symbolTable, heap);
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
    public IExpression deepCopy() {
        return new ArithmeticExpression(e1.deepCopy(), e2.deepCopy(), operation);
    }
    @Override
    public String toString() {
        return e1 + " " +  operation + " " + e2;
    }
    @Override
    public IType typeCheck(ISymbolTable<String, IType> typeTable) throws MyException{
        IType type1, type2;
        type1 = e1.typeCheck(typeTable);
        type2 = e2.typeCheck(typeTable);
        if(type1.equals(new IntType())){
            if(type2.equals(new IntType())){
                return new IntType();
            }
            else throw new OperandIsNotInteger();
        }
        else throw new OperandIsNotInteger();
    }
}
