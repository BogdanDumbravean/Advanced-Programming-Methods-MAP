package Model.Statements;

import Model.Expressions.Expression;
import Model.MyException;
import Model.ProgramState.MyDictionary;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIStack;
import Model.ProgramState.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class WhileStatement implements IStatement {
    private Expression exp;
    private IStatement st;

    public WhileStatement(Expression exp, IStatement st) {
        this.exp = exp;
        this.st = st;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        MyIStack<IStatement> stk = state.getStk();
        MyIDictionary<String, Value> symTbl = state.getSymTable();

        Value cond = exp.eval(symTbl, state.getHeap());
        if (!cond.getType().equals(new BoolType())) {
            throw new MyException("Condition is not boolean");

        }

        BoolValue v = (BoolValue) cond;
        if (v.getVal()) {
            stk.push(new WhileStatement(exp, st));
            stk.push(st);
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type typexp=exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            MyIDictionary<String, Type> typeEnvClone = new MyDictionary<String, Type>();
            typeEnvClone.setContent(typeEnv.getContent());
            st.typecheck(typeEnvClone);
            return typeEnv;
        }
        else
            throw new MyException("The condition of IF has not the type bool");
    }

    @Override
    public String toString() {
        return "while(".concat(exp.toString()).concat(")").concat(st.toString());
    }
}
