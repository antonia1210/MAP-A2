package model.statement;
import model.ProgramState;
import model.adt.ISymbolTable;
import exception.MyException;
import exception.VariableAlreadyDefined;
import model.type.IType;
import model.value.IValue;

public record VariableDeclarationStatement(String name, IType type) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programstate) throws MyException {
        ISymbolTable<String, IValue> symbolTable = programstate.getSymbolTable();
        if (symbolTable.isDefined(name)) {
            throw new VariableAlreadyDefined(name);
        }
        symbolTable.put(name, type.defaultValue());
        return null;
    }
    @Override
    public IStatement deepCopy(){
        return new VariableDeclarationStatement(name, type.deepCopy());
    }
    @Override
    public String toString() {
        return type.toString() + " " + name;
    }

    @Override
    public ISymbolTable<String, IType> typeCheck(ISymbolTable<String, IType> typeTable) throws MyException {
        typeTable.put(name, type);
        return typeTable;
    }
}
