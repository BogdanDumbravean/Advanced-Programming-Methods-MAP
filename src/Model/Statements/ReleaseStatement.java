package Model.Statements;

import Model.MyException;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyISemaphoreTable;
import Model.ProgramState.ProgramState;
import Model.ProgramState.Tuple3;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.List;

public class ReleaseStatement implements IStatement {
    private String var;

    public ReleaseStatement(String var) {
        this.var = var;
    }

    @Override
    public synchronized ProgramState execute(ProgramState state) {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyISemaphoreTable<Tuple3<Integer, List<Integer>, Integer>> sem = state.getSemaphoreTable();

        if (symTbl.isDefined(var)) {
            Value val = symTbl.getValue(var);
            Type typId = (val).getType();

            if (typId instanceof IntType) {
                int intVal = ((IntValue) val).getVal();
                if(sem.isDefined(intVal)) {
                    Tuple3<Integer, List<Integer>, Integer> entry = sem.getValue(intVal);
                    if (entry.t2.contains(state.getId())) {
                        entry.t2.remove((Object)state.getId());
                    }
                }
                else throw new MyException("semaphore with id " + intVal + " not declared");
            } else {
                throw new MyException("declared type of variable " + var + " and type of the assigned expression do not match");
            }
        }
        else
            throw new MyException("the used variable " + var + " was not declared before");

        return null;
    }

    @Override
    public String toString() {
        return "Release(" + var + ")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return null;
    }
}
