package Model.ProgramState;

import java.util.Map;

public interface MyIHeap<T> {

    boolean isDefined(Integer id);

    T getValue(Integer id);

    void update(Integer id, T val);

    void remove(Integer id);

    Integer getNextId();

    void setContent(Map<Integer, T> map);

    Map<Integer, T> getContent();
}
