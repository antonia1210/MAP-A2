package model.expression;

import exception.InvalidHeapAddress;
import exception.MyException;
import exception.VariableNotRefType;
import model.adt.IHeap;
import model.adt.ISymbolTable;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public record ReadHeapExpression(IExpression expression) implements IExpression {
    @Override
    public IValue evaluate(ISymbolTable<String,IValue> symbolTable, IHeap heap) throws MyException{
        IValue value = expression.evaluate(symbolTable, heap);
        if(!(value instanceof RefValue))
            throw new VariableNotRefType(value.toString());
        RefValue refValue = (RefValue)value;
        int address = refValue.getAddress();
        if(!heap.isDefined(address)){
            throw new InvalidHeapAddress(address);
        }
        return heap.get(address);
    }
    @Override
    public IExpression deepCopy() {
        return new ReadHeapExpression(expression.deepCopy());
    }
    @Override
    public String toString() {
        return "Read Heap " + expression;
    }
    @Override
    public IType typeCheck(ISymbolTable<String, IType> typeTable) throws MyException{
        IType type = expression.typeCheck(typeTable);
        if(type instanceof RefType){
            RefType refType = (RefType)type;
            return refType.getInner();
        }
        else throw new VariableNotRefType(expression.toString());
    }
}
