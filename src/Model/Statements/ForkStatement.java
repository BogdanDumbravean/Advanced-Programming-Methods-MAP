package Model.Statements;

import Model.MyException;
import Model.ProgramState.*;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.Value;

public class ForkStatement implements IStatement {
    private IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        MyIStack<IStatement> stk = new MyStack<IStatement>();
        MyIDictionary<String, Value> symTbl = new MyDictionary<String, Value>();
        symTbl.setContent(state.getSymTable().getContent());
        ProgramState newState = new ProgramState(stk, symTbl, state.getOut(), state.getFileTable(), state.getHeap(), state.getSemaphoreTable(), statement, ProgramState.getEmptyId());
        //ProgramState.setEmptyId(ProgramState.getEmptyId()+1);
        return newState;
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        MyIDictionary<String, Type> typeEnvClone = new MyDictionary<String, Type>();
        typeEnvClone.setContent(typeEnv.getContent());
        statement.typecheck(typeEnvClone);
        return typeEnv;
    }
}
