package Model.Expressions;
import Model.MyException;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIHeap;
import Model.Values.*;
import Model.Types.*;

public class ArithmeticExpression implements Expression {
    private Expression e1;
    private Expression e2;
    private char op; //+,-,*,/

    public ArithmeticExpression(char c, Expression a, Expression b) {
        op = c;
        e1 = a;
        e2 = b;
    }

    @Override
    public String toString() {
        return e1.toString() + op + e2.toString();
    }

    @Override
    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        Type typ1, typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            } else
            throw new MyException("second operand is not an integer");
        }else
        throw new MyException("first operand is not an integer");
    }

    @Override
    public Value eval(MyIDictionary<String,Value> tbl, MyIHeap<Value> heap) {
        Value v1,v2;
        v1= e1.eval(tbl, heap);
        if (v1.getType().equals(new IntType())) {
            v2 = e2.eval(tbl, heap);
            if (v2.getType().equals(new IntType())) {
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1,n2;
                n1= i1.getVal();
                n2 = i2.getVal();
                if (op=='+') return new IntValue(n1+n2);
                if (op =='-') return new IntValue(n1-n2);
                if(op=='*') return new IntValue(n1*n2);
                if(op=='/')
                    if(n2==0) throw new MyException("division by zero");
                    else return new IntValue(n1/n2);
            }
            else
                throw new MyException("second operand is not an integer");
        }
        else
            throw new MyException("first operand is not an integer");
        return new IntValue(-1);
    }
}
