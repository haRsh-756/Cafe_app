package com.example.project4fx;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/**
 * main application
 * @author harshpatel, giancarlo andretta
 */
public class RU_CafeApplication extends Application{
    /**
     * method sets the necessary stage setting, style css, and fxml
     * @param stage stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(RU_CafeApplication.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());
        stage.setTitle("RU_Cafe!");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * main method to launch the gui
     * @param args cmd line args
     */
    public static void main(String[] args) {
        launch();
    }
}
