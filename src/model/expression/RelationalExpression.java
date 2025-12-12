package model.expression;

import model.adt.IHeap;
import model.adt.ISymbolTable;
import exception.MyException;
import exception.OperandIsNotInteger;
import exception.UnknownOperator;
import model.type.BoolType;
import model.type.IType;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;

public record RelationalExpression(IExpression left, IExpression right, String operator) implements IExpression {
    @Override
    public IValue evaluate(ISymbolTable<String,IValue> symbolTable, IHeap heap) throws MyException{
        IValue leftValue = left.evaluate(symbolTable, heap);
        if(!leftValue.getType().equals(new IntType())){
            throw new OperandIsNotInteger();
        }
        IValue rightValue = right.evaluate(symbolTable, heap);
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
    @Override
    public IType typeCheck(ISymbolTable<String, IType> typeTable) throws MyException{
        IType type1 = left.typeCheck(typeTable);
        IType type2 = right.typeCheck(typeTable);
        if(type1.equals(new IntType())){
            if(type2.equals(new IntType())){
                return new BoolType();
            }
            else throw new OperandIsNotInteger();
        }
        else throw new OperandIsNotInteger();
    }
}
