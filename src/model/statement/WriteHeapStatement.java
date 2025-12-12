package model.statement;

import exception.*;
import model.ProgramState;
import model.adt.IHeap;
import model.adt.ISymbolTable;
import model.expression.IExpression;
import model.type.IType;
import model.type.RefType;
import model.type.StringType;
import model.value.IValue;
import model.value.RefValue;

public record WriteHeapStatement(String variableName, IExpression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        ISymbolTable<String, IValue> symbolTable = state.getSymbolTable();
        IHeap heap = state.getHeap();
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
        return null;
    }
    @Override
    public IStatement deepCopy() {
        return new WriteHeapStatement(variableName, expression);
    }
    @Override
    public String toString() {
        return "Write Heap " + variableName + " " + expression;
    }
    @Override
    public ISymbolTable<String, IType> typeCheck(ISymbolTable<String, IType> typeTable) throws MyException {
        IType variableType = typeTable.lookup(variableName);
        IType expressionType = expression.typeCheck(typeTable);
        if(!(variableType instanceof RefType refType)){
            throw new MyException(variableName + " is not a RefType");
        }
        if(!expressionType.equals(refType.getInner()))
            throw new TypeMismatch(variableName, expressionType.toString(), refType.getInner().toString());
        return typeTable;
    }
}
