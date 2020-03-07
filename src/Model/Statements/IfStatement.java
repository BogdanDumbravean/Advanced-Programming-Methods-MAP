package Model.Statements;

import Model.Expressions.Expression;
import Model.MyException;
import Model.ProgramState.MyDictionary;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIStack;
import Model.ProgramState.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.*;
//import com.sun.org.apache.xpath.internal.operations.Bool;

public class IfStatement implements IStatement {
    private Expression exp;
    private IStatement thenS;
    private IStatement elseS;

    public IfStatement(Expression e, IStatement t, IStatement el) {
        exp=e;
        thenS=t;
        elseS=el;
    }

    @Override
    public String toString() {
        return "IF("+ exp.toString()+") THEN(" +thenS.toString() +")ELSE("+elseS.toString()+")";
    }

    public ProgramState execute(ProgramState state){
        MyIStack<IStatement> stk = state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();

        Value cond = exp.eval(symTbl, state.getHeap());
        if (cond.getType().equals(new BoolType())) {
            BoolValue v = (BoolValue) cond;
            if (v.getVal())
                stk.push(thenS);
            else
                stk.push(elseS);
        }
        else {
            throw new MyException("Condition is not boolean");
        }

        return null;
    }

    @Override
    public MyIDictionary<String,Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            MyIDictionary<String, Type> typeEnvClone = new MyDictionary<String, Type>();
            typeEnvClone.setContent(typeEnv.getContent());
            thenS.typecheck(typeEnvClone);
            elseS.typecheck(typeEnvClone);
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }
}
