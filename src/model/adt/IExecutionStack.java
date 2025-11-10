package model.adt;

import exception.MyException;

public interface IExecutionStack<T> {
    void push(T element);
    T pop() throws MyException;
    boolean isEmpty();
    String fileToString();
}
