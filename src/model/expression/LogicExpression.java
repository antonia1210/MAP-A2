package model.expression;

import model.adt.SymbolTable;
import model.exception.MyException;
import model.exception.OperandIsNotBoolean;
import model.exception.UnknownOperator;
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
    public String toString() {
        return e1 + " " + operation + " " + e2;
    }
}
