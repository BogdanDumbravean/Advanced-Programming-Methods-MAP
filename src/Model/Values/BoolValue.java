package Model.Values;
import Model.Types.BoolType;
import Model.Types.Type;
//import com.sun.org.apache.xpath.internal.operations.Bool;

public class BoolValue implements Value {
    private boolean val;

    public BoolValue(boolean v) {val = v;}

    @Override
    public Type getType() {
        return new BoolType();
    }

    public boolean getVal() {
        return val;
    }

    @Override
    public String toString() {
        return Boolean.toString(val);
    }

    @Override
    public boolean equals(Object another){
        if(!(another instanceof BoolValue))
            return false;
        BoolValue v = (BoolValue)another;
        return v.val == val;
    }
}
