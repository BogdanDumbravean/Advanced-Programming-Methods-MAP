package Model.Values;

import Model.Types.RefType;
import Model.Types.Type;

public class RefValue implements Value{
    private int address;
    private Type locationType;

    public RefValue(int addr, Type t) {
        address=addr;
        locationType = t;
    }

    @Override
    public String toString() {
        return Integer.toString(address).concat(" ").concat(locationType.toString());
    }

    public int getAddr() {return address;}

    public Type getType() { return new RefType(locationType);}
}