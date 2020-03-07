package Model.Statements;

import Model.MyException;
import Model.ProgramState.MyIDictionary;
import Model.ProgramState.ProgramState;
import Model.Types.Type;

public interface IStatement {
    ProgramState execute(ProgramState state); //which is the execution method for a statement
    MyIDictionary<String,Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
