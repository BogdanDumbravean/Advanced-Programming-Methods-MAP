package Model.Statements;

import Model.Expressions.Expression;
import Model.MyException;
import Model.ProgramState.*;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

import java.util.LinkedList;
import java.util.List;

public class SemaphoreStatement implements IStatement {
    private String var;
    private Expression exp1, exp2;

    public SemaphoreStatement(String var, Expression exp1, Expression exp2) {
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public synchronized ProgramState execute(ProgramState state) {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();

        Value nr1 = exp1.eval(symTbl, heap);
        Value nr2 = exp2.eval(symTbl, heap);

        MyISemaphoreTable<Tuple3<Integer, List<Integer>, Integer>> sem = state.getSemaphoreTable();
        Integer addr = sem.getNextId();
        Tuple3<Integer, List<Integer>, Integer> value = new Tuple3<Integer, List<Integer>, Integer>(((IntValue)nr1).getVal(), new LinkedList<Integer>(), ((IntValue)nr2).getVal());
        sem.update(addr, value);
        if(symTbl.isDefined(var) && symTbl.getValue(var).getType() instanceof IntType) {
            symTbl.update(var, new IntValue(addr));
        }
        else throw new MyException("Var not declared or type not right for semaphore");

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return null;
    }

    @Override
    public String toString(){
        return "new Semaphore(" + var.toString() + ", " + exp1.toString() + ", " + exp2.toString() + ")";
    }
}
