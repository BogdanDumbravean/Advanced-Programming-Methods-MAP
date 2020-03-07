package Model.ProgramState;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MyList<T> implements MyIList<T> {
    private Queue<T> q;

    public MyList() {
        q = new LinkedList<T>();
    }

    public Queue<T> getList() {
        return q;
    }

    @Override
    public boolean isEmpty() {
        return q.isEmpty();
    }

    @Override
    public void add(T v) {
        q.add(v);
    }

    @Override
    public T pop() {
        return q.remove();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (T t : q) {
            out.append(t).append("\n");
        }
        return out.toString();
    }
}
