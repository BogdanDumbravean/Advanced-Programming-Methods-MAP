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

public class WHStatement implements IStatement {
    private String name;
    private Expression exp;

    public WHStatement(String name, Expression exp) {
        this.name = name;
        this.exp = exp;
    }

    @Override
    public synchronized ProgramState execute(ProgramState state) {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        MyIHeap<Value> heap = state.getHeap();

        if(!symTbl.isDefined(name)) {
            throw new MyException(name + " not declared");
        }
        Value nameVal = symTbl.getValue(name);
        if(!nameVal.getType().toString().equals("ref")) {
            throw new MyException(name + " not of reference type");
        }
        Integer addr = ((RefValue)nameVal).getAddr();
        if(!heap.isDefined(addr)) {
            throw new MyException(name + " not in heap");
        }
        Value val = exp.eval(symTbl, heap);
        if(!val.getType().equals(heap.getValue(addr).getType())) {
            throw new MyException("Ref values don't match");
        }

        heap.update(addr, val);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "wH(".concat(name).concat(",").concat(exp.toString()).concat(")");
    }
}
