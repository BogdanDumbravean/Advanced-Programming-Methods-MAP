package Model.Statements;
import Model.Expressions.Expression;
import Model.Expressions.ValueExpression;
import Model.MyException;
import Model.ProgramState.*;
import Model.Types.*;
import Model.Values.*;

public class AssignStatement implements IStatement {
    private String id;
    private Expression exp;

    public AssignStatement(String v, Expression valueExpression) {
        id = v;
        exp = valueExpression;
    }

    @Override
    public String toString() {
        return id + "=" + exp.toString();
    }

    public ProgramState execute(ProgramState state) {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        Value val = exp.eval(symTbl, state.getHeap());
        if (symTbl.isDefined(id)) {
            Type typId = (symTbl.getValue(id)).getType();

            if (val.getType().equals(typId))
                symTbl.update(id, val);
            else {
                throw new MyException("declared type of variable " + id + " and type of the assigned expression do not match");
            }
        }
        else
            throw new MyException("the used variable " + id + " was not declared before");
        return null;
    }

    @Override
    public MyIDictionary<String,Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        Type typevar = typeEnv.getValue(id);
        Type typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else
            throw new MyException("Assignment: right hand side and left hand side have different types ");
    }
}
