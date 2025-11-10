package model.adt;
import exception.MyException;
import exception.StackIsEmpty;
import model.statement.CompoundStatement;
import model.statement.IStatement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
public class ExecutionStack<T> implements IExecutionStack<T> {
    private Stack<T> stack = new Stack<>();
    @Override
    public void push(T element) {stack.push(element);}
    @Override
    public T pop() throws MyException {
        if(stack.isEmpty()) throw new StackIsEmpty();
        return stack.pop();
    }
    @Override
    public boolean isEmpty() {return stack.isEmpty();}
    @Override
    public String toString() {return stack.toString();}
    public List<T> getList() {
        List<T> list = new ArrayList<>(stack);
        Collections.reverse(list);
        return list;
    }
    public ExecutionStack() {this.stack = new Stack<>();}
    ExecutionStack(Stack<T> stack) {this.stack = stack;}
    @Override
    public String fileToString(){
        Stack<IStatement> newStack = (Stack<IStatement>) stack.clone();
        ExecutionStack<IStatement> copy = new ExecutionStack<>(newStack);

        StringBuilder result = new StringBuilder();
        while(!copy.isEmpty()){
            IStatement statement = copy.pop();
            if(statement instanceof CompoundStatement){
                IStatement first = ((CompoundStatement) statement).first();
                IStatement second = ((CompoundStatement) statement).second();
                copy.push(second);
                copy.push(first);
            }
            else{
                result.append(statement.toString()).append("\n");
            }
        }
        return result.toString();
    }
}
