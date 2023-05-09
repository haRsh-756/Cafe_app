package com.example.project4fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * main controller of the ui
 * @author harsh_patel, giancarlo andretta
 */
public class RU_CafeController{
    /**
     * stage instance
     */
    private Stage stage;
    /**
     * scene instance
     */
    private Scene scene;
    /**
     * root instance
     */
    private Parent root;
    /**
     * orders instance
     */
    private static final List<Order> orders = new ArrayList<>();
    /**
     * items list instance
     */
    private static List<MenuItem> items = new ArrayList<>();
    /**
     * order NUm instance
     */
    private static int orderNum;
    /**
     * current order total instance
     */
    private double currentOrderTotal;

    /**
     * switch to main view
     * @param event event
     * @throws IOException
     */
    @FXML
    protected void switchToMainView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * switch to donut view
     * @param event evnet
     * @throws IOException
     */
    @FXML
    protected void switchToDonutsView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DonutsView.fxml"));
        root = loader.load();

        orderDonutsController donutsController = loader.getController();
        donutsController.setMainController(this);
        donutsController.setItems(items);
        items = donutsController.getItems();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * switch to coffee view
     * @param event event
     * @throws IOException
     */
    @FXML
    protected void switchToCoffeeView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CoffeeView.fxml"));
        root = loader.load();

        orderCoffeeController coffeeController = loader.getController();
        coffeeController.setMainController(this);
        coffeeController.setItems(items);
        items = coffeeController.getItems();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * switch to current order view
     * @param event event
     * @throws IOException
     */
    @FXML
    protected void switchToCurrentOrder(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("basketView.fxml"));
        root = loader.load();

        BasketViewController cartViewController = loader.getController();
        cartViewController.setMainController(this);
        cartViewController.setItemsList(items);
        cartViewController.showList();
        cartViewController.fireAmount();
        items = cartViewController.getItemsList();
        orderNum = cartViewController.getOrderNum();
        this.currentOrderTotal = cartViewController.getTotal();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * set up new order
     */
    public void setNewOrder(){
        //this.items = cartViewController.getItemsList();
        //this.orderNum = cartViewController.getOrderNum();
        if(items != null){
            Order order = new Order(orderNum, items);
            order.setTotalForThisOrder(this.currentOrderTotal);
            orders.add(order);
        }
        if(items != null){
            items.clear();
        }
    }

    /**
     * switch to store order view
     * @param event event
     * @throws IOException
     */
    @FXML
    protected void switchToStoreOrder(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StoreOrdersView.fxml"));
        root = loader.load();

        StoreOrdersController storeOrdersController = loader.getController();
        storeOrdersController.setMainController(this);
        //Order order = new Order(orderNum++,this.items);
        //storeOrdersController.setOrder(order);
        storeOrdersController.setOrderList(orders);
        storeOrdersController.showOrders();
        //storeOrdersController.getOrderList();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



   /* @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //differentObjects = new ArrayList<>();
        System.out.println("new");
        //orders = new ArrayList<>();
        //items = new ArrayList<>();
        //System.out.println(items.size());
        //Donut donuts = new Donut();
        // ObservableList<String> options = FXCollections.observableArrayList(
        //         Donut.Donuts.DONUT_HOLE.name(), Donut.Donuts.CAKE.name(), Donut.Donuts.YEAST.name()
        /// );
        // donutType.setItems(options);
    }*/

}