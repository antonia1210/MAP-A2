package controller;

import model.ProgramState;
import model.exception.MyException;
import model.exception.UnknownOperator;

public interface IController {
    ProgramState oneStep(ProgramState programState) throws MyException;
    void allSteps() throws MyException;
}
