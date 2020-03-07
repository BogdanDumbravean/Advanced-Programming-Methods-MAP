package Model.Values;

import Model.Types.StringType;
import Model.Types.Type;

public class StringValue implements Value {
    private String val;

    public StringValue(String v) {val = v;}

    public String getVal() {
        return val;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return val;
    }

    @Override
    public boolean equals(Object another){
        if(!(another instanceof StringValue))
            return false;
        StringValue v = (StringValue)another;
        return v.val.equals(val);
    }
}
