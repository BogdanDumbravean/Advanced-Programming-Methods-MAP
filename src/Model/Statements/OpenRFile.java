package Model.Statements;

import Model.Expressions.Expression;
import Model.MyException;
import Model.ProgramState.*;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements IStatement {
    private Expression exp;

    public OpenRFile(Expression exp) {this.exp = exp;}

    @Override
    public ProgramState execute(ProgramState state) {
        MyIStack<IStatement> stk = state.getStk();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        Value cond = exp.eval(state.getSymTable(), state.getHeap());
        if (!cond.getType().equals(new StringType())) {
            throw new MyException("Value is not String");
        }

        StringValue v = (StringValue) cond;
        if (fileTable.isDefined(v)) {
            throw new MyException(v + " already declared");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(v.getVal()));

            state.getFileTable().update(v, br);
        }
        catch (IOException e) {
            throw new MyException(e.getMessage());
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type type = exp.typecheck(typeEnv);
        if (!type.equals(new StringType())) {
            throw new MyException("Value is not String");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "Open File ".concat(exp.toString());
    }
}
