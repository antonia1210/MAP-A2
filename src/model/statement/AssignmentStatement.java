package model.statement;

import model.ProgramState;
import model.adt.SymbolTable;
import exception.MyException;
import exception.TypeNotFound;
import exception.VariableIsNotDefined;
import model.expression.IExpression;
import model.type.IType;
import model.value.IValue;


public record AssignmentStatement(String id, IExpression expression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyException{
        SymbolTable<String, IValue> symbolTable = programState.getSymbolTable();
        if (!symbolTable.isDefined(id)) {
            throw new VariableIsNotDefined(id);
        }
        IValue IValue = expression.evaluate(symbolTable);
        IType ITypeId = (symbolTable.lookup(id)).getType();

        if (IValue.getType().equals(ITypeId))
            symbolTable.update(id, IValue);
        else throw new TypeNotFound();
        return programState;
    }
    @Override
    public IStatement deepCopy() {
        return new AssignmentStatement(id, expression.deepCopy());
    }

    @Override
    public String toString() {
        return id + " = " + expression.toString();
    }
}
