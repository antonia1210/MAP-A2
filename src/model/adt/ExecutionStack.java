package model.adt;
import model.exception.MyException;
import model.exception.StackIsEmpty;

import java.util.EmptyStackException;
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
}
