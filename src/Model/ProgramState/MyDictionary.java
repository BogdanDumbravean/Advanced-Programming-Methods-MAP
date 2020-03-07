package Model.ProgramState;

import Model.Values.Value;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<T1, T2> implements MyIDictionary<T1, T2> {
    private Map<T1, T2> dict;

    public MyDictionary() {
        dict = new HashMap<T1, T2>();
    }

    @Override
    public boolean isDefined(T1 id) {
        return dict.containsKey(id);
    }

    @Override
    public T2 getValue(T1 id) {
        return dict.get(id);
    }

    @Override
    public void update(T1 id, T2 val) {
        dict.put(id, val);
    }

    @Override
    public void remove(T1 id) {
        dict.remove(id);
    }

    @Override
    public Map<T1, T2> getContent() {
        return dict;
    }

    @Override
    public void setContent(Map<T1, T2> map) {
        dict = map;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (T1 t : dict.keySet()) {
            out.append(t).append("-->").append(dict.get(t)).append("\n");
        }
        return out.toString();
    }
}
