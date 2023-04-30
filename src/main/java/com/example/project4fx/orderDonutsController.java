package com.example.project4fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

/**
 * order donuts controller
 * @author harsh_patel, giancarlo andretta
 */
public class orderDonutsController implements Initializable {
    /**
     * combo box instance
     */
    @FXML private ComboBox<String> donutType;
    /**
     * list view instance
     */
    @FXML private ListView<String> addedItemsView;
    /**
     * list view instance
     */
    @FXML private ListView<String> listViewoptions;
    /**
     * quantity instance
     */
    @FXML private ComboBox<Integer> quantity;
    /**
     * img instance
     */
    @FXML private ImageView img;
    /**
     *  textfield instance
     */
    @FXML private TextField printPrice;
    /**
     * button instance
     */
    @FXML private Button addToOrder;
    /**
     * button instance
     */
    @FXML private Button home;
    /**
     * button instance
     */
    @FXML private Button viewCart;
    /**
     * list of menu items
     */
    private List<MenuItem> items;
    /**
     * option instance
     */
    private ObservableList<String> options1;
    /**
     * options instance
     */
    private ObservableList<String> options2;
    /**
     * options instance
     */
    private ObservableList<String> options3;
    /**
     * yeast donuts list instance
     */
    private final ArrayList<String> yeastDonutsList = new ArrayList<>();
    /**
     * cake donuts list
     */
    private final ArrayList<String> cakeDonutsList = new ArrayList<>();
    /**
     * donut holes list instance
     */
    private final ArrayList<String> donut_holesList = new ArrayList<>();
    /**
     * donut object instance
     */
    private Donut donut;
    /**
     * get selected item instance
     */
    private String getSelectedItem;
    /**
     * decimal format instance
     */
    DecimalFormat df = new DecimalFormat("$#0.00");
    /**
     * qty
     */
    private int qty;
    /**
     * controller instance
     */
    private RU_CafeController controller;

    /**
     * instance of main controller
     * @param controller main controller
     */

    public void setMainController(RU_CafeController controller){
        this.controller = controller;
    }

    /**
     * get items list
     * @return items list
     */
    public List<MenuItem> getItems() {
        return items;
    }

    /**
     * set items
     * @param items list of items
     */
    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    /**
     *  switch to main view
     * @param event event
     * @throws IOException
     */
    @FXML
    public void switchToMainView(ActionEvent event) throws IOException {
        this.controller.switchToMainView(event);
    }

    /**
     * switch to cart view
     * @param event event
     * @throws IOException
     */
    @FXML
    public void switchToCartView(ActionEvent event) throws IOException {
        this.controller.switchToCurrentOrder(event);
    }

    /**
     * get selected donut
     * @param event event
     */
    @FXML public void getDonutType(ActionEvent event){
        if(donutType.getSelectionModel().isSelected(0)) {
            img.setImage(new Image((new File("src/main/resources/imgs/donutstock.jpeg")).toURI().toString()));
            listViewoptions.setItems(options1);
            getSelectedItem = listViewoptions.getSelectionModel().getSelectedItem();
            donut.setDonutType(DonutType.YEAST);
        }
        else if(donutType.getSelectionModel().isSelected(1)){
            img.setImage(new Image((new File("src/main/resources/imgs/cake_Donuts.jpeg")).toURI().toString()));
            listViewoptions.setItems(options2);
            getSelectedItem = listViewoptions.getSelectionModel().getSelectedItem();
            donut.setDonutType(DonutType.CAKE);
        }
        else if(donutType.getSelectionModel().isSelected(2)){
            img.setImage(new Image((new File("src/main/resources/imgs/Donut-Holes.jpeg")).toURI().toString()));
            listViewoptions.setItems(options3);
            getSelectedItem = listViewoptions.getSelectionModel().getSelectedItem();
            donut.setDonutType(DonutType.DONUT_HOLE);
        }
        updateSubTotal(event);
    }

    /**
     * get quantity
     * @param event event
     */
    @FXML protected void getQuantity(ActionEvent event){
        qty = quantity.getSelectionModel().getSelectedItem();
        //donut.setQty(qty);
    }

    /**
     * add items
     * @param event event
     */
    @FXML protected void rightArrow(ActionEvent event){
        getQuantity(event);
        getDonutType(event);
        if(getSelectedItem != null) {
            String itemAndQty = getSelectedItem + "(" + qty + ")";
            //listViewoptions.getItems().remove(getSelectedItem);
            addedItemsView.getItems().add(itemAndQty);
            listViewoptions.getItems().remove(getSelectedItem);
        }
        updateSubTotal(event);
    }

    /**
     * remove items
     * @param event event
     */
    @FXML
    protected void leftArrow(ActionEvent event){
        String selectedItem = addedItemsView.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            addedItemsView.getItems().remove(selectedItem);
            String itemName = selectedItem.substring(0,selectedItem.lastIndexOf("("));
            DonutType getDonutType = donut.find(itemName,yeastDonutsList,cakeDonutsList,donut_holesList);
            if(getDonutType != null){
                if(getDonutType == DonutType.YEAST){
                    options1.add(selectedItem.substring(0,selectedItem.lastIndexOf("(")));
                }
                else if(getDonutType == DonutType.CAKE){
                    options2.add(selectedItem.substring(0,selectedItem.lastIndexOf("(")));
                }
                else if(getDonutType == DonutType.DONUT_HOLE){
                    options3.add(selectedItem.substring(0,selectedItem.lastIndexOf("(")));
                }
            }
        }
        updateSubTotal(event);
    }

    /**
     * updates sub total
     * @param event event
     */
    @FXML
    protected void updateSubTotal(ActionEvent event){
        try {
            double subTotal = 0.0;
            if (!addedItemsView.getItems().isEmpty()) {
                for (int i = 0; i < addedItemsView.getItems().size(); i++) {
                    String item = addedItemsView.getItems().get(i).substring(0, addedItemsView.getItems().get(i).lastIndexOf("("));
                    String temp = addedItemsView.getItems().get(i);
                    int qty = Integer.parseInt(temp.replaceAll("[\\D]",""));
                    DonutType getdonutType = donut.find(item,yeastDonutsList,cakeDonutsList,donut_holesList);
                    donut.setDonutType(getdonutType);
                    donut.setQty(qty);
                    if(getdonutType != null) {
                        if (getdonutType == DonutType.YEAST) {
                            subTotal += donut.itemPrice();
                        }
                        else if (getdonutType == DonutType.CAKE) {
                            subTotal += donut.itemPrice();
                        }
                        else if (getdonutType == DonutType.DONUT_HOLE) {
                            subTotal += donut.itemPrice();
                        }
                    }
                }
            }
            printPrice.setText(df.format(subTotal));
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
    }

    /**
     * add to order btn
     * @param event event
     * @throws IOException
     */
    @FXML
    protected void addToOrderBtn(ActionEvent event) throws IOException {
        if(!addedItemsView.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Add to Order?");
            alert.setContentText("Click OK to confirm or CANCEL to cancel the order");
            // option != null.
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                for(int i = 0; i < addedItemsView.getItems().size(); i++){
                    String itemName = addedItemsView.getItems().get(i).substring(0, addedItemsView.getItems().get(i).lastIndexOf("("));
                    String temp = addedItemsView.getItems().get(i);
                    int qty = Integer.parseInt(temp.replaceAll("[\\D]",""));
                    DonutType getdonutType = donut.find(itemName,yeastDonutsList,cakeDonutsList,donut_holesList);
                    donut.setDonutType(getdonutType);
                    Donut newDonut = new Donut(donut.getDonutType(),itemName,qty);
                    if(!this.items.contains(newDonut)) {
                        this.items.add(newDonut);
                    }
                    else{
                        for (MenuItem item : this.items) {
                            if (item instanceof Donut d) {
                                if (d.equals(newDonut)) {
                                    int newQty = d.getQty() + qty;
                                    d.setQty(newQty);
                                }
                            }
                        }
                    }
                }
                addedItemsView.getItems().clear();
                resetOptions();
                getDonutType(event);
            }
        }
        else{
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText("Nothing to Add");
            alert1.setContentText("Please select donuts !!");
            alert1.showAndWait();
        }
    }
    private void resetOptions(){
        options1 = FXCollections.observableArrayList(yeastDonutsList);
        options2 = FXCollections.observableArrayList(cakeDonutsList);
        options3 = FXCollections.observableArrayList(donut_holesList);
    }
    /**
     * initialize method
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //items = new ArrayList<>();
        img.setImage(new Image((new File("src/main/resources/imgs/donutstock.jpeg")).toURI().toString()));
        ObservableList<String> options = FXCollections.observableArrayList(
                "Yeast Donuts", "Cake Donuts", "Donut holes"
        );
        Collections.addAll(yeastDonutsList, "jelly", "glazed", "strawberry frosted", "vanilla frosted", "sugar", "crispy creme");
        Collections.addAll(cakeDonutsList,"old fashioned", "glazed blueberry", "cinnamon sugar", "apple cider", "chocolate glazed");
        Collections.addAll(donut_holesList,"glazed holes", "glazed chocolate holes", "jelly holes", "pumpkin holes");
        options1 = FXCollections.observableArrayList(yeastDonutsList);
        options2 = FXCollections.observableArrayList(cakeDonutsList);
        options3 = FXCollections.observableArrayList(donut_holesList);
        ObservableList<Integer> numbers = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12);
        donut = new Donut();
        donutType.setItems(options);
        quantity.setItems(numbers);
        quantity.getSelectionModel().select(0);
        //donutType.getSelectionModel().select(0);
    }
}
//this.controller.orders.add(new Donut(donut.getDonutType(),itemName,qty));
//this.controller.orders.add(donut);
//this.controller.switchToCurrentOrder(event);
/*else if (option.get() == ButtonType.CANCEL) {
                System.out.println("cancel");
            }*/
/*options1 = FXCollections.observableArrayList(
                "jelly", "glazed", "strawberry frosted", "vanilla frosted", "sugar", "crispy creme"
        );
        options2 = FXCollections.observableArrayList(
                "old fashioned", "glazed blueberry", "cinnamon sugar", "apple cider", "chocolate glazed"
        );
        options3 = FXCollections.observableArrayList(
                "glazed holes", "glazed chocolate holes", "jelly holes", "pumpkin holes"
        );*/
//yeastDonutsList = new ArrayList<>(options1);
//cakeDonutsList = new ArrayList<>(options2);
//donut_holesList = new ArrayList<>(options3);