package Model.Statements;

import Model.MyException;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.ProgramState;
import Model.Types.Type;

public class NopStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state){
        return null;
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }
}
