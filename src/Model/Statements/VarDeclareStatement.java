package Model.Statements;

import Model.MyException;
import Model.ProgramState.*;
import Model.Types.*;
import Model.Values.*;

public class VarDeclareStatement implements IStatement {
    private String name;
    private Type type;

    public VarDeclareStatement(String v, Type t) {
        name = v;
        type = t;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }

    public ProgramState execute(ProgramState state){
        MyIDictionary<String, Value> symTbl = state.getSymTable();

        if (symTbl.isDefined(name)) {
            throw new MyException(name + " already declared");
        }

        symTbl.update(name, type.defaultValue());

        return null;
    }

    @Override
    public MyIDictionary<String,Type> typecheck(MyIDictionary<String,Type> typeEnv) throws MyException{
        typeEnv.update(name,type);
        return typeEnv;
    }

}
