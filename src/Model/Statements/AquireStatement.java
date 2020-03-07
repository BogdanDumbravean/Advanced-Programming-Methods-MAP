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

import java.sql.Statement;
import java.util.List;

public class AquireStatement implements IStatement {
    private String var;

    public AquireStatement(String var) {
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
                    if((entry.t1 - entry.t3) > entry.t2.size()) {
                        if (!entry.t2.contains(state.getId())) {
                            entry.t2.add(state.getId());
                        }
                    } else {
                        state.getStk().push(new AquireStatement(var));
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
        return "Aquire(" + var + ")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return null;
    }
}
