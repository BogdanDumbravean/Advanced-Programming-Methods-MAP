package Model.Statements;

import Model.Expressions.Expression;
import Model.Expressions.VarExpression;
import Model.MyException;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIList;
import Model.ProgramState.ProgramState;
import Model.Types.Type;
import Model.Values.Value;

public class PrintStatement implements IStatement {
    private Expression exp;

    public PrintStatement(Expression v) {
        exp = v;
    }

    @Override
    public String toString(){
        return "print(" + exp.toString() + ")";
    }

    public ProgramState execute(ProgramState state){
        MyIList<Value> out = state.getOut();
        out.add(exp.eval(state.getSymTable(), state.getHeap()));
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

}
