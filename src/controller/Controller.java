package controller;

import model.ProgramState;
import model.adt.ExecutionStack;
import exception.MyException;
import model.statement.IStatement;
import repository.IRepository;

public class Controller implements IController {
    private IRepository repository;
    private boolean displayFlag;
    public Controller(IRepository repository) {
        this.repository = repository;
    }
    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }
    @Override
    public ProgramState oneStep(ProgramState programState) throws MyException {
        ExecutionStack<IStatement>  executionStack = programState.getExecutionStack();
        if (executionStack.isEmpty()) {
            throw new MyException("Program has no execution stack");
        }
        IStatement current_statement = executionStack.pop();
        return current_statement.execute(programState);
    }

    @Override
    public void allSteps() throws MyException {
        ProgramState programState = repository.getCurrentProgram();
        repository.logProgramStateExecution();

        while(!programState.getExecutionStack().isEmpty()) {
            oneStep(programState);
            repository.logProgramStateExecution();
        }
    }
}
