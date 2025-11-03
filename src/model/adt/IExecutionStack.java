package model.adt;

import model.exception.MyException;

public interface IExecutionStack<T> {
    void push(T element);
    T pop() throws MyException;
    boolean isEmpty();
}
