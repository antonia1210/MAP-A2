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
import java.util.stream.Collectors;
import java.util.Collections;

public class Controller implements IController {
    private final IRepository repository;
    private boolean displayFlag;
    public Controller(IRepository repository) {
        this.repository = repository;
    }
    public void setDisplayFlag(boolean displayFlag) {
        this.displayFlag = displayFlag;
    }
    private List<Integer> getAddressFromSymbolTable(Collection<IValue> symbolTableValues) {
        return symbolTableValues.stream().filter(v -> v instanceof RefValue).map(v->((RefValue) v).getAddress()).collect(Collectors.toList());
    }
    private List<Integer> getAddressFromHeap(Collection<IValue> heapValues) {
        return heapValues.stream().filter(v-> v instanceof RefValue).map(v->((RefValue) v).getAddress()).collect(Collectors.toList());
    }
    private Map<Integer,IValue> garbageCollector(Collection<IValue> symbolTableValues, Map<Integer,IValue> heap) {
        Set<Integer> reachable = new HashSet<>(getAddressFromSymbolTable(symbolTableValues));
        boolean changed = true;
        while (changed) {
            List<Integer> newAddresses = heap.entrySet().stream().filter(e->reachable.contains(e.getKey()))
                    .flatMap(e->getAddressFromHeap(Collections.singletonList(e.getValue())).stream())
                    .filter(address -> !reachable.contains(address)).collect(Collectors.toList());
            changed = !newAddresses.isEmpty();
            reachable.addAll(newAddresses);
        }
        return heap.entrySet().stream().filter(e->reachable.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public ProgramState oneStep(ProgramState programState) throws MyException {
        ExecutionStack<IStatement>  executionStack = programState.getExecutionStack();
        if (executionStack.isEmpty()) {
            throw new StackIsEmpty();
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
            Map<Integer,IValue> newHeap = garbageCollector(programState.getSymbolTable().getAll().values(), programState.getHeap().getAll());
            programState.getHeap().setContent(newHeap);
        }
            repository.logProgramStateExecution();
    }
}
