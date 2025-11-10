package model.statement;

import model.ProgramState;
import exception.MyException;

public interface IStatement {
    ProgramState execute(ProgramState programState) throws MyException;
    IStatement deepCopy();
}
