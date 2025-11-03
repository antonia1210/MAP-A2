package model.statement;
import model.ProgramState;
import model.adt.SymbolTable;
import model.exception.MyException;
import model.exception.TypeNotFound;
import model.exception.VariableAlreadyDefined;
import model.exception.VariableIsNotDefined;
import model.type.IType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.IValue;

public record VariableDeclarationStatement(String name, IType IType) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programstate) throws MyException {
        SymbolTable<String, IValue> symbolTable = programstate.getSymbolTable();
        if (symbolTable.isDefined(name)) {
            throw new VariableAlreadyDefined(name);
        }
        IValue defaultIValue = switch (IType.toString()) {
            case "int" -> new IntValue(0);
            case "bool" -> new BoolValue(false);
            default -> throw new TypeNotFound();
        };
        symbolTable.put(name, defaultIValue);
        return programstate;
    }
    @Override
    public String toString() {
        return IType.toString() + " " + name;
    }
}
