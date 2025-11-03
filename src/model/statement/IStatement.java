package model.statement;

import model.ProgramState;
import model.exception.MyException;

public interface IStatement {
    ProgramState execute(ProgramState programState) throws MyException;
}
