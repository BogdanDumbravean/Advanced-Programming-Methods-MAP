package Model.Statements;

import Model.Expressions.Expression;
import Model.MyException;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIHeap;
import Model.ProgramState.ProgramState;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class NewStatement implements IStatement {
    private String name;
    private Expression exp;

    public NewStatement(String name, Expression exp) {
        this.name = name;
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        MyIDictionary<String, Value> symTbl = state.getSymTable();

        if (!symTbl.isDefined(name)) {
            throw new MyException(name + " doesn't exist");
        }
        Type varType = symTbl.getValue(name).getType();
        Value expVal = exp.eval(symTbl, state.getHeap());
        if (!varType.equals(new RefType(expVal.getType()))) {
            throw new MyException(name + " doesn't have the right type");
        }

        MyIHeap<Value> h = state.getHeap();
        Integer addr = h.getNextId();
        h.update(addr, expVal);
        symTbl.update(name, new RefValue(addr, expVal.getType()));

        return null;
    }

    @Override
    public MyIDictionary<String,Type> typecheck(MyIDictionary<String,Type> typeEnv) throws
            MyException{
        Type typevar = typeEnv.getValue(name);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp)))
            return typeEnv;
        else
            throw new MyException("NEW stmt: right hand side and left hand side have different types ");
    }


    @Override
    public String toString() {
        return "new(".concat(name).concat(",").concat(exp.toString()).concat(")");
    }
}
