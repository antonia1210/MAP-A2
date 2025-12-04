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
    public void logProgramStateExecution(ProgramState programState) throws MyException{
        try(PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath,true)))){
            logFile.println("ID " + programState.getId());
            logFile.println("Execution Stack:");
            logFile.println(programState.getExecutionStack().fileToString());
            logFile.println("Symbol Table:");
            programState.getSymbolTable().getAll().forEach((var,v)->logFile.println(var + "-->" + v));
            logFile.println("Out:");
            programState.getOut().getList().forEach(val -> logFile.println(val.toString()));
            logFile.println("File Table:");
            programState.getFileTable().getTable().forEach((fileName, br) -> logFile.println(fileName.toString()));
            logFile.println("Heap:");
            programState.getHeap().getAll().forEach((address,value) -> logFile.println(address + "-->" + value));
            logFile.println("---------------------------------------------------");
            logFile.println();
        }
        catch (IOException e){
            throw new MyException(e.getMessage());
        }
    }
    @Override
    public List<ProgramState> getProgramList() throws MyException{
        if (programStates.isEmpty()) {
            throw new MyException("No program state available in the list");
        }
        return  programStates;
    }
    @Override
    public void setProgramList(List<ProgramState> programList) {
        this.programStates.clear();
        this.programStates.addAll(programList);
    }

}
