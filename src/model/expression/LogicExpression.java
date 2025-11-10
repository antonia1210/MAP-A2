package model.expression;

import model.adt.SymbolTable;
import exception.MyException;
import exception.OperandIsNotBoolean;
import exception.UnknownOperator;
import model.type.BoolType;
import model.value.IValue;
import model.value.BoolValue;

public record LogicExpression(IExpression e1, IExpression e2, String operation) implements IExpression {
    @Override
    public IValue evaluate(SymbolTable<String, IValue> symbolTable) throws MyException {
        IValue v1 = e1.evaluate(symbolTable);
        if(!v1.getType().equals(new BoolType())) {
            throw new OperandIsNotBoolean();
        }
        IValue v2 = e2.evaluate(symbolTable);
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
}
