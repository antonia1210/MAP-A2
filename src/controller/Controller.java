package controller;

import exception.StackIsEmpty;
import model.ProgramState;
import model.adt.ExecutionStack;
import exception.MyException;
import model.statement.IStatement;
import model.value.IValue;
import model.value.RefValue;
import repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.Collections;

public class Controller implements IController {
    private final IRepository repository;
    private boolean displayFlag;
    ExecutorService executor;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }

    private List<Integer> getAddressFromSymbolTable(Collection<IValue> symbolTableValues) {
        return symbolTableValues.stream().filter(v -> v instanceof RefValue).map(v -> ((RefValue) v).getAddress()).collect(Collectors.toList());
    }

    private List<Integer> getAddressFromHeap(Collection<IValue> heapValues) {
        return heapValues.stream().filter(v -> v instanceof RefValue).map(v -> ((RefValue) v).getAddress()).collect(Collectors.toList());
    }

    private Map<Integer, IValue> garbageCollector(Collection<IValue> symbolTableValues, Map<Integer, IValue> heap) {
        Set<Integer> reachable = new HashSet<>(getAddressFromSymbolTable(symbolTableValues));
        boolean changed = true;
        while (changed) {
            List<Integer> newAddresses = heap.entrySet().stream().filter(e -> reachable.contains(e.getKey()))
                    .flatMap(e -> getAddressFromHeap(Collections.singletonList(e.getValue())).stream())
                    .filter(address -> !reachable.contains(address)).collect(Collectors.toList());
            changed = !newAddresses.isEmpty();
            reachable.addAll(newAddresses);
        }
        return heap.entrySet().stream().filter(e -> reachable.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    @Override
    public void oneStepForAllPrograms(List<ProgramState> programsList) throws InterruptedException{
        programsList.forEach(programState -> repository.logProgramStateExecution(programState));
        List<Callable<ProgramState>> callList = programsList.stream().map((ProgramState p) -> (Callable<ProgramState>)(()-> p.oneStep())).collect(Collectors.toList());
        List<ProgramState> newProgramsList = executor.invokeAll(callList).stream().map(future->{try{return future.get();} catch(InterruptedException e){
            Thread.currentThread().interrupt();
            return null;
        } catch(ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof MyException me) {
                throw new RuntimeException(me);
            } else {
                throw new RuntimeException(cause);
            }
        }}).filter(p->p!=null).collect(Collectors.toList());
        programsList.addAll(newProgramsList);
        programsList.forEach(program->repository.logProgramStateExecution(program));
        repository.setProgramList(programsList);
    }

    @Override
    public void allSteps() throws MyException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programList = removeCompletedProgram(repository.getProgramList());
        while(programList.size()>0){
            Map<Integer, IValue> newHeap = garbageCollector(programList.stream().flatMap(p->p.getSymbolTable().getAll().values().stream()).collect(Collectors.toList()),
                    programList.get(0).getHeap().getAll());
            programList.get(0).getHeap().setContent(newHeap);
            try {
                oneStepForAllPrograms(programList);
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
            programList = removeCompletedProgram(repository.getProgramList());
        }
        executor.shutdownNow();
        repository.setProgramList(programList);
    }

    @Override
    public List<ProgramState> removeCompletedProgram(List<ProgramState> inProgramsList) {
        return inProgramsList.stream().filter(p->p.isNotCompleted()).collect(Collectors.toList());
    }
}
