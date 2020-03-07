package sample;

import javafx.beans.property.SimpleStringProperty;

public class SemaphoreGUIEntry {
    private SimpleStringProperty index, value, listOfValues;

    public SemaphoreGUIEntry(String index, String value, String listOfValues) {
        this.index = new SimpleStringProperty(index);
        this.value = new SimpleStringProperty(value);
        //System.out.println(listOfValues);
        this.listOfValues = new SimpleStringProperty(listOfValues);
    }

    public String getIndex() {
        return index.get();
    }

    public void setIndex(String index) {
        this.index.set(index);
    }

    public String getValue() {
        return value.get();
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public String getListOfValues() {
        return listOfValues.get();
    }

    public void setListOfValues(String listOfValues) {
        this.listOfValues.set(listOfValues);
    }
}
