package Model.Expressions;
import Model.MyException;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIHeap;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class ValueExpression implements Expression {
    private Value e;

    public ValueExpression(Value intValue) {
        e = intValue;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) {
        return e;
    }

    @Override
    public String toString() {
        return e.toString();
    }

    @Override
    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException {
        return e.getType();
    }
}
