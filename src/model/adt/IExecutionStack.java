package model.adt;

import exception.MyException;

import java.util.Stack;

public interface IExecutionStack<T> {
    void push(T element);
    T pop() throws MyException;
    boolean isEmpty();
    String fileToString();
    Stack<T> getAll();
}
