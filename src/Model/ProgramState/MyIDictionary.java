package Model.ProgramState;

import java.util.Map;

public interface MyIDictionary<T1, T2> {

    boolean isDefined(T1 id);

    T2 getValue(T1 id);

    void update(T1 id, T2 val);

    void remove(T1 id);

    Map<T1, T2> getContent();
    void setContent(Map<T1, T2> map);
}
