package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import View.View;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader root = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("Main Window");
        primaryStage.setScene(new Scene(root.load(), 530, 810));
        primaryStage.show();

        Stage secondStage = new Stage();
        secondStage.setTitle("Select Program");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        secondStage.setScene(new Scene(loader.load(), 600, 300));
        Controller controller = loader.<Controller>getController();
        controller.initData(root.<MainWindowController>getController());
        secondStage.show();


        //View.main();
    }


    public static void main(String[] args) {
        launch(args);
    }




}
