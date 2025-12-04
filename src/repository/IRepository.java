package repository;
import model.ProgramState;
import exception.MyException;

import java.util.List;

public interface IRepository{
void addProgram(ProgramState program);
void logProgramStateExecution(ProgramState state) throws MyException;
List<ProgramState> getProgramList() throws MyException;
void setProgramList(List<ProgramState> programList);
}
