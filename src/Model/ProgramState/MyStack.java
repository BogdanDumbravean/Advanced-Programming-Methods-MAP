package Model.ProgramState;

import Model.Types.Type;

import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    private Stack<T> stack;

    public MyStack() {
        stack = new Stack<T>();
    }

    public Stack<T> getStack() {
        return stack;
    }

    public T pop() {
        return stack.pop();
    }
    public void push(T v) {
        stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (T t : stack) {
            out.append(t).append("\n");
        }
        return out.toString();
    }
}
