package Model.Expressions;

import Model.MyException;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIHeap;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class LogicExpression implements Expression {
    private Expression e1;
    private Expression e2;
    private int op;     // 1 - and; 2 - or

    @Override
    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Value> heap) {
        Value v1,v2;
        v1= e1.eval(tbl, heap);
        if (v1.getType().equals(new BoolType())) {
            v2 = e2.eval(tbl, heap);
            if (v2.getType().equals(new BoolType())) {
                BoolValue i1 = (BoolValue) v1;
                BoolValue i2 = (BoolValue) v2;
                boolean n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if (op == 1) return new BoolValue(n1 && n2);
                else if (op == 2) return new BoolValue(n1 || n2);
            } else
                throw new MyException("second operand is not a boolean");
        }
        else
            throw new MyException("first operand is not a boolean");
        return new BoolValue(false);
    }

    @Override
    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            } else
            throw new MyException("second operand is not boolean");
        }else
        throw new MyException("first operand is not boolean");
    }
}
