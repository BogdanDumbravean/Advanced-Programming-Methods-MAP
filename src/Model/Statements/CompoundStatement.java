package Model.Statements;

import Model.MyException;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIStack;
import Model.ProgramState.ProgramState;
import Model.Types.Type;

public class CompoundStatement implements IStatement {
    private IStatement first;
    private IStatement second;

    public CompoundStatement(IStatement v, IStatement v1) {
        first = v;
        second = v1;
    }

    @Override
    public String toString() {
        return "("+first.toString() + ";" + second.toString()+")";
    }

    public ProgramState execute(ProgramState state){
        MyIStack<IStatement> stk=state.getStk();
        stk.push(second);
        stk.push(first);
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException {
        //MyIDictionary<String,Type> typEnv1 = first.typecheck(typeEnv);
        //MyIDictionary<String,Type> typEnv2 = snd.typecheck(typEnv1);
        //return typEnv2;
        return second.typecheck(first.typecheck(typeEnv));
    }

}

