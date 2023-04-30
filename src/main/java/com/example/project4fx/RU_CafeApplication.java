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
/*public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}*/
/*public class RU_CafeApplication extends Application {

    @FXML
    private BorderPane root;
    @FXML
    private VBox menu;
    @FXML
    private StackPane viewContainer; // Add this field


    private void createMenu() {
        Button donutsButton = new Button("Donuts");
        Button coffeeButton = new Button("Coffee");
        Button addonsButton = new Button("Addons");

        donutsButton.setOnAction(event -> switchView(loadFXML("DonutsView.fxml")));
        coffeeButton.setOnAction(event -> switchView(loadFXML("CoffeeView.fxml")));
        addonsButton.setOnAction(event -> switchView(loadFXML("BasketView.fxml")));

        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(10);
        menu.getChildren().addAll(donutsButton, coffeeButton, addonsButton);
    }

    private Parent loadFXML(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML: " + fxml, e);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(RU_CafeApplication.class.getResource("MainView.fxml"));
        root = fxmlLoader.load();
        menu = (VBox) fxmlLoader.getNamespace().get("menu");
        viewContainer = (StackPane) fxmlLoader.getNamespace().get("viewContainer"); // Initialize this field

        createMenu();

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle("RU Cafe");
        stage.show();
    }

    private void switchView(Parent newView) {
        viewContainer.getChildren().setAll(newView);
    }

    public static void main(String[] args) {
        launch();
    }
}*/


