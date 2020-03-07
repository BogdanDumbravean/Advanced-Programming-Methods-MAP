package Model.ProgramState;

import Model.MyException;
import Model.Statements.IStatement;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.util.List;


public class ProgramState {
    private MyIStack<IStatement> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private MyIHeap<Value> heap;
    private MyISemaphoreTable<Tuple3<Integer, List<Integer>, Integer>> semaphoreTable;
    private int id;
    private static int emptyId;
   // private IStatement originalProgram; //optional field, but good to have

    public ProgramState(MyIStack<IStatement> stk, MyIDictionary<String,Value> symtbl, MyIList<Value> ot,
                        MyIDictionary<StringValue, BufferedReader> fileTbl, MyIHeap<Value> hp, MyISemaphoreTable<Tuple3<Integer, List<Integer>, Integer>> semTable,
                        IStatement prg, int id){
        exeStack=stk;
        symTable=symtbl;
        out = ot;
        fileTable = fileTbl;
        heap = hp;
        semaphoreTable = semTable;
        this.id = id;
       // originalProgram=deepCopy(prg);//recreate the entire original prg
        stk.push(prg);
    }

    public int getId() { return id; }

    public synchronized static int getEmptyId() { emptyId += 10; return emptyId;  }
    //public static void setEmptyId(int newId) { emptyId = newId; }

    public MyIStack<IStatement> getStk() {
        return exeStack;
    }

    public MyIDictionary<String, Value> getSymTable() {
        return symTable;
    }

    public MyIList<Value> getOut() {
        return out;
    }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public MyIHeap<Value> getHeap() {
        return heap;
    }

    public MyISemaphoreTable<Tuple3<Integer, List<Integer>, Integer>> getSemaphoreTable() {return semaphoreTable;}

    public boolean isNotCompleted() { return !exeStack.isEmpty(); }

    public ProgramState oneStep() throws MyException {
        if(exeStack.isEmpty())
            throw new MyException("Program State stack is empty");
        IStatement crtStmt = exeStack.pop();
        return crtStmt.execute(this);
    }
}
