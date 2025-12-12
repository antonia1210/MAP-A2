package model.statement;

import exception.MyException;
import exception.OperandIsNotBoolean;
import exception.OperandIsNotInteger;
import model.ProgramState;
import model.adt.IExecutionStack;
import model.adt.ISymbolTable;
import model.expression.IExpression;
import model.type.BoolType;
import model.type.IType;
import model.value.BoolValue;
import model.value.IValue;

public record WhileStatement(IExpression condition, IStatement statement) implements IStatement{
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        IValue value = condition.evaluate(state.getSymbolTable(), state.getHeap());
        if(!value.getType().equals(new BoolType()))
            throw new OperandIsNotBoolean();
        BoolValue boolValue = (BoolValue) value;
        IExecutionStack<IStatement> stack = state.getExecutionStack();
        if(boolValue.getValue()){
            stack.push(this);
            stack.push(statement);
        }
        return null;
    }
    @Override
    public IStatement deepCopy(){
        return new WhileStatement(condition, statement);
    }
    @Override
    public String toString(){
        return "While " + condition.toString() + " " + statement.toString();
    }

    @Override
    public ISymbolTable<String, IType> typeCheck(ISymbolTable<String, IType> typeTable) throws MyException {
        IType conditionType = condition.typeCheck(typeTable);
        if(conditionType.equals(new BoolType())){
            statement.typeCheck(typeTable.deepCopy());
            return typeTable;
        }
        else throw new MyException("Condition of WHILE is not boolean");
    }
}
