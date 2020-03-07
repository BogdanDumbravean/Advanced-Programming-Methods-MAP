package sample;

import javafx.beans.property.SimpleStringProperty;

public class GUIDataEntrySymbolTable {
    private SimpleStringProperty variableName, value;

    public GUIDataEntrySymbolTable(String variableName, String value) {
        this.variableName = new SimpleStringProperty(variableName);
        this.value = new SimpleStringProperty(value);
    }

    public String getVariableName() {
        return variableName.get();
    }

    public void setVariableName(String variableName) {
        this.variableName.set(variableName);
    }

    public String getValue() {
        return value.get();
    }

    public void setValue(String value) {
        this.value.set(value);
    }
}