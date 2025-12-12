package model.expression;

import model.adt.IHeap;
import model.adt.ISymbolTable;
import exception.MyException;
import exception.OperandIsNotBoolean;
import exception.UnknownOperator;
import model.type.BoolType;
import model.type.IType;
import model.value.IValue;
import model.value.BoolValue;

public record LogicExpression(IExpression e1, IExpression e2, String operation) implements IExpression {
    @Override
    public IValue evaluate(ISymbolTable<String, IValue> symbolTable, IHeap heap) throws MyException {
        IValue v1 = e1.evaluate(symbolTable, heap);
        if(!v1.getType().equals(new BoolType())) {
            throw new OperandIsNotBoolean();
        }
        IValue v2 = e2.evaluate(symbolTable,heap);
        if(!v2.getType().equals(new BoolType())) {
            throw new OperandIsNotBoolean();
        }
        boolean b1 = ((BoolValue) v1).getValue();
        boolean b2 = ((BoolValue) v2).getValue();
        return switch(operation){
            case "and" -> new BoolValue(b1 && b2);
            case "or" -> new BoolValue(b1 || b2);
            default -> throw new UnknownOperator();
        };
    }
    @Override
    public IExpression deepCopy() {
        return new LogicExpression(e1.deepCopy(), e2.deepCopy(), operation);
    }
    @Override
    public String toString() {
        return e1 + " " + operation + " " + e2;
    }
    @Override
    public IType typeCheck(ISymbolTable<String, IType> typeTable) throws MyException{
        IType type1 = e1.typeCheck(typeTable);
        IType type2 = e2.typeCheck(typeTable);
        if(type1.equals(new BoolType())){
            if(type2.equals(new BoolType())){
                return new BoolType();
            }
            else throw new OperandIsNotBoolean();
        }
        else throw new OperandIsNotBoolean();
    }
}
