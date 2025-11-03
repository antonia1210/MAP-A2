package repository;
import model.ProgramState;
import model.exception.MyException;

import java.util.List;
public interface IRepository{
ProgramState getCurrentProgram() throws MyException;
void addProgram(ProgramState program);
}
