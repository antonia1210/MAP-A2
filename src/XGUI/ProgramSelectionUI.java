package XGUI;

import controller.IController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.statement.IStatement;

import java.util.List;

public class ProgramSelectionUI extends Application {

    public static List<IStatement> programs;
    public static List<IController> controller;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Select a program");
        ListView<String> listView = new ListView<>();
        for(IStatement statement : programs){
            listView.getItems().add(statement.toString());
        }
        Button selectButton = new Button("Select Program");
        selectButton.setOnAction(e -> {
           int selectedIndex = listView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                MainUI.controller = controller.get(selectedIndex);
                MainUI.program = programs.get(selectedIndex);
                try {
                    new MainUI().start(new Stage());
                    primaryStage.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        VBox box = new VBox(10, listView, selectButton);
        Scene scene = new Scene(box,600,400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
