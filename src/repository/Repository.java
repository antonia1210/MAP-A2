package repository;

import model.ProgramState;
import model.exception.MyException;

import java.util.ArrayList;
import java.util.List;
public class Repository implements IRepository {
    public final List<ProgramState> programStates;
    public Repository() {
        this.programStates = new ArrayList<>();
    }
    @Override
    public void addProgram(ProgramState program) {
        this.programStates.add(program);
    }
    @Override
    public ProgramState getCurrentProgram() throws MyException{
        if (programStates.isEmpty()) {
            throw new MyException("No program state available in the list");
        }
        return programStates.get(0);
    }
}
