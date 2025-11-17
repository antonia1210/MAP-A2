package model.statement;

import exception.*;
import model.ProgramState;
import model.adt.Heap;
import model.adt.SymbolTable;
import model.expression.IExpression;
import model.type.IType;
import model.value.IValue;
import model.value.RefValue;

public record WriteHeapStatement(String variableName, IExpression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        SymbolTable<String, IValue> symbolTable = state.getSymbolTable();
        Heap heap = state.getHeap();
        if(!symbolTable.isDefined(variableName))
            throw new VariableIsNotDefined(variableName);
        IValue variableValue = symbolTable.lookup(variableName);
        if(!(variableValue instanceof RefValue))
            throw new VariableNotRefType(variableName);
        RefValue refValue = (RefValue) variableValue;
        int address = refValue.getAddress();
        if(!heap.isDefined(address))
            throw new InvalidHeapAddress(address);
        IValue evaluated_value = expression.evaluate(symbolTable, heap);
        IType locationType = refValue.getLocationType();
        if(!evaluated_value.getType().equals(locationType))
            throw new TypeMismatch(variableName, locationType.toString(), evaluated_value.getType().toString());
        heap.put(address, evaluated_value);
        return state;
    }
    @Override
    public IStatement deepCopy() {
        return new WriteHeapStatement(variableName, expression);
    }
    @Override
    public String toString() {
        return "Write Heap " + variableName + " " + expression;
    }
}
