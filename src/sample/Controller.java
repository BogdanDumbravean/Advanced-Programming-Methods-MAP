package sample;

import Model.Commands.Command;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import View.View;
import javafx.scene.input.MouseEvent;

//import static View.View.CreateCmd;

public class Controller implements Initializable {
    private MainWindowController mwc;
    @FXML
    private ListView<String> list;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> items = FXCollections.observableArrayList();

        for(var i : View.getExamples())
            items.add(i.toString());
        list.setItems(items);
    }

    public void initData(MainWindowController c) {
        mwc = c;
    }

    public void onClick() {
        String selected = list.getSelectionModel().getSelectedItem();
        for(var i : View.getExamples())
            if (i.toString().equals(selected)) {
                mwc.updateCommand(i);
            }
    }
}
