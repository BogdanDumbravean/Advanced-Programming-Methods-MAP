package Repository;

import Model.MyException;
import Model.ProgramState.ProgramState;

import java.io.IOException;
import java.util.List;

public interface Repository {
    List<ProgramState> getPrgList();
    void setPrgList(List<ProgramState> prgList);
    void setLogPath(String path);
    void logPrgStateExec(ProgramState state) throws MyException, IOException;
}
