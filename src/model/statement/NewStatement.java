package model.statement;

import exception.MyException;
import exception.TypeMismatch;
import exception.VariableIsNotDefined;
import model.ProgramState;
import model.adt.Heap;
import model.adt.IHeap;
import model.adt.ISymbolTable;
import model.adt.SymbolTable;
import model.expression.IExpression;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public record NewStatement(String variableName, IExpression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable<String, IValue> symbolTable = state.getSymbolTable();
        Heap heap = state.getHeap();
        if(!symbolTable.isDefined(variableName)){
            throw new VariableIsNotDefined(variableName);
        }
        IValue value = symbolTable.lookup(variableName);
        if(!(value.getType() instanceof RefType)){
            throw new VariableIsNotDefined(variableName);
        }
        IValue evaluated_value = expression.evaluate(symbolTable, heap);
        IType locationType = ((RefType) value.getType()).getInner();
        if(!evaluated_value.getType().equals(locationType)){
            throw new TypeMismatch(variableName, locationType.toString(), evaluated_value.getType().toString());
        }
        int newAddress = heap.allocate(evaluated_value);
        symbolTable.update(variableName, new RefValue(newAddress, locationType));
        return state;
    }
    @Override
    public IStatement deepCopy() {
        return new NewStatement(variableName, expression);
    }
    @Override
    public String toString(){
        return "new " + variableName + ", " + expression;
    }
}
