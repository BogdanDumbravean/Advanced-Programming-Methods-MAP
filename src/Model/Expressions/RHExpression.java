package Model.Expressions;

import Model.MyException;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.MyIHeap;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;

public class RHExpression implements Expression {
    private Expression exp;

    public RHExpression(Expression exp) {
        this.exp = exp;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> table, MyIHeap<Value> heap) {
        Value v = exp.eval(table, heap);
        if(!v.getType().toString().equals("ref")) {
            throw new MyException("Expression not of reference type");
        }

        Integer addr = ((RefValue)v).getAddr();
        if (!heap.isDefined(addr)) {
            throw new MyException("Address doesn't exist");
        }
        return heap.getValue(addr);
    }

    @Override
    public String toString() {
        return exp.toString();
    }

    @Override
    public Type typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        Type typ=exp.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft =(RefType) typ;
            return reft.getInner();
        } else
            throw new MyException("the rH argument is not a Ref Type");
    }

}
