package Model.Expressions;
import Model.MyException;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIHeap;
import Model.Types.Type;
import Model.Values.Value;

public interface Expression {
    Value eval(MyIDictionary<String,Value> table, MyIHeap<Value> heap);
    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
