package repository;

import model.ProgramState;
import exception.MyException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    public final List<ProgramState> programStates;
    public final String logFilePath;
    public Repository(String logFilePath) {
        this.programStates = new ArrayList<>();
        this.logFilePath = logFilePath;
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
    @Override
    public void logProgramStateExecution() throws MyException{
        ProgramState programState = getCurrentProgram();
        try(PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath,true)))){
            logFile.println("Execution Stack:");
            for (var statement : programState.getExecutionStack().getList()) {
                logFile.println(statement.toString());
            }
            logFile.println("Symbol Table:");
            programState.getSymbolTable().getAll().forEach((var,v)->logFile.println(var + "-->" + v));
            logFile.println("Out:");
            programState.getOut().getList().forEach(val -> logFile.println(val.toString()));
            logFile.println("File Table:");
            programState.getFileTable().getTable().forEach((fileName, br) -> logFile.println(fileName.toString()));
            logFile.println("---------------------------------------------------");
            logFile.println();
        }
        catch (IOException e){
            throw new MyException(e.getMessage());
        }

    }
}
