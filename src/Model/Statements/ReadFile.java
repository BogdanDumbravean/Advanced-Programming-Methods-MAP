package Model.Statements;

import Model.Expressions.Expression;
import Model.MyException;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.ProgramState;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStatement {
    private Expression exp;
    private String varName;

    public ReadFile(Expression exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        MyIDictionary<String, Value> symTbl = state.getSymTable();
        if (!symTbl.isDefined(varName)) {
            throw new MyException(varName + " not declared");
        }
        Value v = symTbl.getValue(varName);
        if(!v.getType().equals(new IntType())) {
            throw new MyException(varName + " not int");
        }

        v = exp.eval(symTbl, state.getHeap());
        if(!v.getType().equals(new StringType())) {
            throw new MyException("Expression not string");
        }

        if(!state.getFileTable().isDefined((StringValue) v)) {
            throw new MyException("File not declared");
        }
        BufferedReader br = state.getFileTable().getValue((StringValue) v);
        String line = null;
        try {
            line = br.readLine();
        }
        catch (IOException e) {
            throw new MyException("Couldn't read line");
        }
        if(line == null)
            symTbl.update(varName, new IntValue(0));
        else
            symTbl.update(varName, new IntValue(Integer.parseInt(line)));

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "Read File ".concat(exp.toString()).concat(" in var ").concat(varName);
    }
}
