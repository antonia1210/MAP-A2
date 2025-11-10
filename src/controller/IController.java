package controller;

import model.ProgramState;
import exception.MyException;

public interface IController {
    ProgramState oneStep(ProgramState programState) throws MyException;
    void allSteps() throws MyException;
}
