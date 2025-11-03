package model.statement;

import model.ProgramState;
import model.adt.SymbolTable;
import model.exception.MyException;
import model.exception.TypeNotFound;
import model.exception.VariableIsNotDefined;
import model.expression.IExpression;
import model.type.IType;
import model.value.IValue;


public record AssignmentStatement(String id, IExpression IExpression) implements IStatement {
    @Override
    public ProgramState execute(ProgramState programState) throws MyException{
        SymbolTable<String, IValue> symbolTable = programState.getSymbolTable();
        if (!symbolTable.isDefined(id)) {
            throw new VariableIsNotDefined(id);
        }
        IValue IValue = IExpression.evaluate(symbolTable);
        IType ITypeId = (symbolTable.lookup(id)).getType();

        if (IValue.getType().equals(ITypeId))
            symbolTable.update(id, IValue);
        else throw new TypeNotFound();
        return programState;
    }

    @Override
    public String toString() {
        return id + " = " + IExpression.toString();
    }
}
