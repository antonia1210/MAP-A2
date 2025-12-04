package controller;

import model.ProgramState;
import exception.MyException;

import java.util.List;

public interface IController {
    void allSteps() throws MyException;
    List<ProgramState> removeCompletedProgram(List<ProgramState> inProgramsList);
    void oneStepForAllPrograms(List<ProgramState> programsList) throws InterruptedException;
}
