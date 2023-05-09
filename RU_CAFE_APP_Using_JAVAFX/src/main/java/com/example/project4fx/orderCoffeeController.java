package com.example.project4fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * order coffee controller class
 * @author harshpatel, giancarlo andretta
 */
public class orderCoffeeController implements Initializable {
    /**
     *check box instance
     */
    @FXML private CheckBox frenchVanilla;
    /**
     *check box instance
     */
    @FXML private CheckBox sweetCream;
    /**
     *check box instance
     */
    @FXML private CheckBox caramel;
    /**
     *check box instance
     */
    @FXML private CheckBox mocha;
    /**
     *check box instance
     */
    @FXML private CheckBox irishCream;
    /**
     * black checkbox instance
     */
    @FXML private CheckBox black;
    /**
     * size select instance
     */
    @FXML private ComboBox<String> sizeSelect;
    /**
     * combobox instance
     */
    @FXML private ComboBox<Integer> quantity;
    /**
     * text field instance
     */
    @FXML private TextField subTotal;
    /**
     * button instance
     */
    @FXML private Button home;
    /**
     * button instance
     */
    @FXML private Button addToOrder;
    /**
     * button instance
     */
    @FXML private Button viewbasket;
    /**
     * main controller instance
     */
    private RU_CafeController controller;
    /**
     * items list
     */
    private List<MenuItem> items;
    /**
     * addIns
     */
    private ArrayList<String> addIns;
    /**
     * cup size
     */
    private int cup_Size;
    /**
     * qty instance
     */
    private int qty;
    /**
     * coffee object instance
     */
    private Coffee coffee;
    /**
     * decimal format instance
     */
    private DecimalFormat df = new DecimalFormat("$#0.00");

    /**
     * main controller
     * @param controller instance
     */
    public void setMainController(RU_CafeController controller){
        this.controller = controller;
    }

    /**
     * get items
     * @return items list
     */
    public List<MenuItem> getItems() {
        return items;
    }

    /**
     * items list
     * @param items items
     */
    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    /**
     * switch to main view event handler
     * @param event event
     * @throws IOException
     */
    @FXML
    protected void switchToMainView(ActionEvent event) throws IOException {
        this.controller.switchToMainView(event);
    }

    /**
     * switch to cart view event handler
     * @param event event
     * @throws IOException
     */
    @FXML protected void switchToCartView(ActionEvent event) throws IOException {
        this.controller.switchToCurrentOrder(event);
    }

    /**
     * add to order event handler
     * @param event event
     */
    @FXML
    protected void addToOrder(ActionEvent event){
        getQuantity(event);
        getCoffee(event);
        getCUP_SIZE(event);
        if(!sizeSelect.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Add to Order?");
            alert.setContentText("Click OK to confirm or CANCEL to cancel the order");
            Optional<ButtonType> option = alert.showAndWait();
            if(option.get() == ButtonType.OK){
                Coffee newCoffee = new Coffee(cup_Size,addIns,qty);
                if(!this.items.contains(coffee)) {
                    this.items.add(newCoffee);
                }
                else{
                    for (MenuItem item : this.items) {
                        if (item instanceof Coffee c) {
                            if (c.equals(newCoffee)) {
                                int newQty = c.getQty() + qty;
                                c.setQuantity(newQty);
                            }
                        }
                    }
                }
            }
        }
        else{
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("Size not found");
            alert1.setContentText("Please select size !!");
            alert1.showAndWait();
        }
    }

    /**
     * get quantity
     * @param event event
     */
    @FXML
    protected void getQuantity(ActionEvent event){
        if(!sizeSelect.getSelectionModel().isEmpty()) {
            qty = quantity.getSelectionModel().getSelectedItem();
            coffee.setQuantity(qty);
            updateSubTotal(event);
        }
    }
    /**
     * get cup size event handler
     */
    @FXML
    protected void getCUP_SIZE(ActionEvent event){
        if(sizeSelect.getSelectionModel().isSelected(0)){
            cup_Size = 0;
            black.setSelected(true);
        }
        else if(sizeSelect.getSelectionModel().isSelected(1)){
            cup_Size = 1;
            black.setSelected(true);
        }
        else if(sizeSelect.getSelectionModel().isSelected(2)){
            cup_Size = 2;
            black.setSelected(true);
        }
        else if(sizeSelect.getSelectionModel().isSelected(3)){
            cup_Size = 3;
            black.setSelected(true);
        }
        getQuantity(event);
        coffee.setCupSize(cup_Size);
        coffee.setQuantity(qty);
        updateSubTotal(event);
    }
    /**
     * get coffee event handler
     * @param event event
     */
    @FXML
    protected void getCoffee(ActionEvent event){
        addIns = new ArrayList<>();
        if(sweetCream.isSelected()){
            addIns.add("Sweet cream");
            black.setSelected(false);
            black.setDisable(true);
        }
        else{
            black.setSelected(true);
            black.setDisable(false);
        }
        if(frenchVanilla.isSelected()){
            addIns.add("French vanilla");
        }
        if(caramel.isSelected()){
            addIns.add("Caramel");
        }
        if(mocha.isSelected()){
            addIns.add("Mocha");
        }
        if(irishCream.isSelected()){
            addIns.add("Irish cream");
        }
        if(black.isSelected()){
            addIns.add("Black");
        }
        getQuantity(event);
        coffee.setQuantity(qty);
        coffee.setCupSizeAndAddIns(cup_Size,addIns);
        updateSubTotal(event);
        //coffee.setQuantity(qty);
        //subTotal.setText(String.valueOf(df.format(coffee.getCoffeePrice())));
        //coffee = new Coffee(cup_Size,addIns);
    }

    /**
     * update Subtotal event handler
     * @param event event
     */
    @FXML
    protected void updateSubTotal(ActionEvent event){
        subTotal.setText(String.valueOf(df.format(coffee.itemPrice())));
    }

    /**
     * initialize method
     * @param url url
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        coffee = new Coffee();
        ObservableList<String> options = FXCollections.observableArrayList("Short", "Tall",
                "Grande", "Venti"
        );
        ObservableList<Integer> numbers = FXCollections.observableArrayList(1,2,3,4,5);
        sizeSelect.setItems(options);
        quantity.setItems(numbers);
        quantity.getSelectionModel().select(0);
        //sizeSelect.getSelectionModel().select(0);
        //black.setSelected(true);
    }
}
 /*@FXML
    protected void updateSubTotal(ActionEvent event){
        DecimalFormat df = new DecimalFormat("#0.00");
        subTotal.setText(String.valueOf(df.format(coffee.getCoffeePrice())));
    }*/
/*private void disableBlack(){
        if(frenchVanilla.isSelected() || sweetCream.isSelected() || caramel.isSelected()
                || mocha.isSelected() || irishCream.isSelected()){
            black.setDisable(true);
        }
    }*/