package Model.ProgramState;

import java.util.HashMap;
import java.util.Map;

public class MySemaphore<T> implements MyISemaphoreTable<T> {
    private Integer freeId = 1;
    private Map<Integer, T> dict;

    public MySemaphore() {
        dict = new HashMap<Integer, T>();
    }

    @Override
    public synchronized boolean isDefined(Integer id) {
        return dict.containsKey(id);
    }

    @Override
    public synchronized T getValue(Integer id) {
        return dict.get(id);
    }

    @Override
    public synchronized void update(Integer id, T val) {
        if(id.equals(freeId))
            freeId++;
        dict.put(id, val);
    }

    @Override
    public synchronized void remove(Integer id) {
        dict.remove(id);
    }

    @Override
    public synchronized Integer getNextId() {
        return freeId;
    }

    @Override
    public void setContent(Map<Integer, T> map) {
        dict = map;
    }

    @Override
    public Map<Integer, T> getContent() {
        return dict;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Integer t : dict.keySet()) {
            out.append(t).append("-->").append(dict.get(t)).append("\n");
        }
        return out.toString();
    }
}