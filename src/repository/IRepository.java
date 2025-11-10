package repository;
import model.ProgramState;
import exception.MyException;

public interface IRepository{
ProgramState getCurrentProgram() throws MyException;
void addProgram(ProgramState program);
void logProgramStateExecution() throws MyException;
}
