package com.example.project4fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

/**
 BasketViewController class represents the controller for the basket view in a Cafe ordering system.
 It manages the display of items in the basket, calculates the sub total, sales tax, and total amount,
 and provides functionality for removing items, placing an order, and switching to the main view.
 It also communicates with the main controller and updates the UI accordingly.
 @author harshpatel, giancarlo andretta
 */
public class BasketViewController{
    /**
     * ListView to display items in the basket
     */
    @FXML private ListView<MenuItem> ItemsSoFar;
    /**
     * TextField to display the sub total amount
     */
    @FXML private TextField subTotal;
    /**
     * TextField to display the sales tax amount
     */
    @FXML private TextField salesTax;
    /**
     * TextField to display the total amount
     */
    @FXML private TextField total;
    /**
     * List to store the items in the basket
     */
    private List<MenuItem> itemList;
    /**
     * Tax rate for calculating sales tax
     */
    private final double TAXRATE = 0.06625;
    /**
     * Variable to store the sales tax amount
     */
    private double sales_Tax;
    /**
     * Variable to store the sub total amount
     */
    private double sub_Total;
    /**
     * Variable to store the total amount
     */
    private double Total;
    /**
     * Instance of the main controller
     */
    private RU_CafeController controller;
    /**
     * Static variable to store the order number
     */
    private static int OrderNum = 1;
    /**
     *  Decimal format for formatting currency values
     */
    private DecimalFormat df = new DecimalFormat("$#0.00");
    /**
     * sets main controller instance
     */
    public void setMainController(RU_CafeController controller){
        this.controller = controller;
    }

    /**
     * sets menuitem
     * @param itemsList items list
     */
    public void setItemsList(List<MenuItem> itemsList){
        this.itemList = itemsList;
    }

    /**
     * list of items
     * @return list of menuitem
     */
    public List<MenuItem> getItemsList(){
        return this.itemList;
    }

    /**
     * get total
     * @return total
     */
    public double getTotal() {
        return Total;
    }
    /**
     * sets total
     */
    public void setTotal(double total) {
        Total = total;
    }
    /**
     * sets order
     */
    public void setOrderNum(int orderNum){
        OrderNum = orderNum;
    }

    /**
     * gets order
     * @return order
     */
    public int getOrderNum() {
        return OrderNum;
    }

    /**
     * switches to main view
     * @param event event
     * @throws IOException
     */
    @FXML
    protected void switchToMainView(ActionEvent event) throws IOException {
        this.controller.switchToMainView(event);
    }

    /**
     * removes selected item
     * @param event event
     */
    @FXML
    protected void removeSelectedItem(ActionEvent event){
        MenuItem selectedmenuItem = ItemsSoFar.getSelectionModel().getSelectedItem();
        if(selectedmenuItem != null){
            this.itemList.remove(selectedmenuItem);
            this.ItemsSoFar.getItems().remove(selectedmenuItem);
        }
        fireAmount();
    }

    /**
     * places the order
     * @param event event
     */
    @FXML
    protected void placeOrder(ActionEvent event){
        if(!ItemsSoFar.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Place Order?");
            alert.setContentText("Click OK to confirm or CANCEL to cancel the order");
            // option != null.
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                OrderNum++;
                this.controller.setNewOrder();
                ItemsSoFar.getItems().clear();
                subTotal.setText(df.format(0));
                salesTax.setText(df.format(0));
                total.setText(df.format(0));
            }
        }
        else{
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("Empty Basket!");
            alert1.setContentText("Looks like cart is Empty !!");
            alert1.showAndWait();
        }
    }

    /**
     * show list of menu item
     */
    protected void showList(){
       ObservableList<MenuItem> display = FXCollections.observableArrayList(this.itemList);
       ItemsSoFar.setItems(display);
    }

    /**
     * fires amount
     */
    @FXML
    protected void fireAmount(){
        sub_Total = 0.0;
        sales_Tax = 0.0;
        Total = 0.0;
        for (MenuItem menuItem : this.itemList) {
            if (menuItem instanceof Coffee coffee) {
                sub_Total += coffee.itemPrice();
                //double salesTax = coffee.itemPrice();
            } else if (menuItem instanceof Donut donut) {
                //subTotal.setText(String.valueOf(donut.itemPrice()));
                sub_Total += donut.itemPrice();
            }
        }
        sales_Tax = sub_Total * TAXRATE;
        Total = sub_Total + sales_Tax;
        setTotal(Total);
        subTotal.setText(df.format(sub_Total));
        salesTax.setText(df.format(sales_Tax));
        total.setText(df.format(Total));
    }

   /* @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemList = getItemsList();
        for(int i =0; i < this.itemList.size(); i++){
            System.out.println(this.itemList.get(i).toString());
        }
        //ItemsSoFar.setItems((ObservableList<MenuItem>) this.controller.items);
        //fireAmount();
    }*/
}
