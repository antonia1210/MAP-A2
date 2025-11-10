package model.adt;
import exception.MyException;
import exception.StackIsEmpty;
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
}
