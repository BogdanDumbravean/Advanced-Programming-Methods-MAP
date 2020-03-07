package Repository;

import Model.MyException;
import Model.ProgramState.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Repo implements Repository {
    private List<ProgramState> state;
    private String logFilePath;

    public Repo(List<ProgramState> s, String path) {
        state = s;
        logFilePath = path;
    }

    @Override
    public List<ProgramState> getPrgList() {
        return state;
    }

    @Override
    public void setPrgList(List<ProgramState> prgList) {
        state = prgList;
    }

    @Override
    public void setLogPath(String path) {
        logFilePath = path;
    }

    @Override
    public void logPrgStateExec(ProgramState state) throws MyException, IOException {
        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.println("ID: " + state.getId());
        logFile.println("ExeStack:");
        logFile.println(state.getStk());
        logFile.println("Heap:");
        logFile.println(state.getHeap());
        logFile.println("SymTable:");
        logFile.println(state.getSymTable());
        logFile.println("Out:");
        logFile.println(state.getOut());
        logFile.println("FileTable:");
        logFile.println(state.getFileTable());
        logFile.close();
    }
}
