package Model.Expressions;

import Model.MyException;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIHeap;
import Model.Types.Type;
import Model.Values.Value;

public class VarExpression implements Expression {
    private String id;

    public VarExpression(String v) {
        id = v;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Value> heap) {
        return tbl.getValue(id);
    }

    @Override
    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException {
        return typeEnv.getValue(id);
    }
}
