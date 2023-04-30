package com.example.project4fx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * store orders controller
 * @author harshpatel, giancarlo andretta
 */
public class StoreOrdersController {
    /**
     * button instance
     */
    @FXML
    private Button cancelOrder;
    /**
     * list view instance
     */
    @FXML
    private ListView<Order> displayOrders;
    /**
     * button instance
     */
    @FXML
    private Button exportOrder;
    /**
     * button instance
     */
    @FXML
    private Button home;
    /**
     * combobox instance
     */
    @FXML
    private ComboBox<Integer> orderNum;
    /**
     * textfield instance
     */
    @FXML
    private TextField ordersTotal;
    /**
     * decimal format instance
     */
    DecimalFormat df = new DecimalFormat("$#0.00");
    /**
     * controller instance
     */
    private RU_CafeController controller;
    /**
     * order instance
     */
    private Order order;
    /**
     * list instance
     */
    private List<Order> orderList;

    /**
     * set method
     */
    public void setMainController(RU_CafeController controller) {
        this.controller = controller;
    }

    /**
     * switch to main view
     *
     * @param event event
     * @throws IOException
     */
    @FXML
    public void switchToMainView(ActionEvent event) throws IOException {
        this.controller.switchToMainView(event);
    }

    /**
     * order
     *
     * @return order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * order
     *
     * @param order order
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * order
     *
     * @return order
     */
    public List<Order> getOrderList() {
        return orderList;
    }

    /**
     * order
     *
     * @param orderList order
     */
    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    /**
     * show orders
     */
    protected void showOrders() {
        setOrderNumbers();
    }

    /**
     * cancel order
     *
     * @param event order
     */
    @FXML
    protected void cancelOrder(ActionEvent event) {
        if (!this.orderList.isEmpty() && !orderNum.getSelectionModel().isEmpty()) {
            int temp = orderNum.getSelectionModel().getSelectedItem();
            int index = orderNum.getSelectionModel().getSelectedIndex();
            for (Order order : this.orderList) {
                if (order.getOrderNum() == temp) {
                    this.order = order;
                }
            }
            orderNum.getItems().remove(index);
            ordersTotal.setText(df.format(0));
            orderList.remove(order);
            displayOrders.getItems().clear();
            displayOrderOnSelection(event);
        }
    }

    /**
     * display orders
     *
     * @param event event
     */
    @FXML
    protected void displayOrderOnSelection(ActionEvent event) {

        if (!this.orderList.isEmpty() && !orderNum.getSelectionModel().isEmpty()) {
            int temp = orderNum.getSelectionModel().getSelectedItem();

            for (Order selectedOrder : orderList) {
                if (selectedOrder.getOrderNum() == temp) {
                    ObservableList<Order> displaySelectedOrder = FXCollections.observableArrayList(selectedOrder);
                    displayOrders.setItems(displaySelectedOrder);
                    ordersTotal.setText(df.format(selectedOrder.getTotalForThisOrder()));
                }
            }
        }
    }

    /**
     * export orders
     */
    @FXML
    protected void exportOrders() {
        /*try (FileOutputStream fs = new FileOutputStream("orders.txt")) {
            ObjectOutputStream os = new ObjectOutputStream(fs);
            for (Order order : orderList) {
                os.writeObject(order);
            }
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("orders.txt"))) {
            // Create and write objects to the text file
            for(Order order: orderList){
                writer.write(order.toString());
                writer.newLine();
            }
            //writer.write(person1.toString());
           // writer.newLine();
           // writer.write(person2.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Objects written to file successfully.");
    }

    /**
     * set order nums
     */
    private void setOrderNumbers() {
        if (!this.orderList.isEmpty()) {
            ObservableList<Integer> orderNums = FXCollections.observableArrayList();
            for (Order value : this.orderList) {
                orderNums.add(value.getOrderNum());
            }
            orderNum.setItems(orderNums);
        }
    }

    /**
     * test bed main to test exported objects
     *
     * @param args
     */
    public static void main(String[] args) {
        /*try {
            // Create a FileInputStream to read from a file
            FileInputStream fis = new FileInputStream("orders.txt");
            // Create an ObjectInputStream to read objects from the file
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Read the array of Person objects from the ObjectInputStream
            //Order[] orders = (Order[]) ois.readObject();

            // Close the ObjectInputStream and FileInputStream
            ois.close();
            fis.close();

            // Use the read objects
            for (Order order : orders) {
                System.out.println(order);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        try (BufferedReader reader = new BufferedReader(new FileReader("orders.txt"))) {
            // Read and process objects from the text file
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    /*public static void main(String[] args) {
        try(FileInputStream fi = new FileInputStream("orders.txt")){
            ArrayList<Object> objectsList = new ArrayList<>();
            boolean cont = true;
            try (ObjectInputStream input = new ObjectInputStream(fi)) {
            while (cont) {
                    Object obj = input.readObject();
                    if (obj != null) {
                        objectsList.add(obj);
                    } else {
                        cont = false;
                    }
                }
            }
            for(Object o: objectsList){
                if(o instanceof Order){
                    /*if(((Order) o).getMenuItemList() instanceof MenuItem menuItem) {
                    //    if (menuItem instanceof Donut donut) {
                    //        System.out.println(donut);
                    //    } else if (menuItem instanceof Coffee coffee) {
                            System.out.println(coffee);
                    //    }
                    //}
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}*/
//@FXML
    /*protected void fireTotalAmount(){
        double Total = 0.0;
        if(!this.orderList.isEmpty()) {
            for(Order value: this.orderList){
                if(value.getOrderNum() == tempNum){
                    Total = value.getTotalForThisOrder();
                }
            }
            /*for (Order value : this.orderList) {
                Total += value.getTotalForThisOrder();
            }
        }
        ordersTotal.setText(df.format(Total));
    }*/
/*
for (;;) {
                Object object = os.readObject();
                if(object instanceof MenuItem){
                    if(object instanceof Coffee coffee){
                        System.out.println(coffee);
                    }else if(object instanceof Donut donut){
                        System.out.println(donut);
                    }
                }
            }
 */
/*
//displayOrderOnSelection(event);
            //orderNum.setItems(newOrderNums);
            //orderNum.getItems().remove(temp);
            //Order order = displayOrders.getSelectionModel().getSelectedItem();
            //displayOrders.getItems().remove(order);
            //orderList.remove(order);
            //orderNum.getItems().clear();
            /*ObservableList<Integer> newOrderNums = FXCollections.observableArrayList();
            for (Order value : this.orderList) {
                newOrderNums.add(value.getOrderNum());
            }
            orderNum.setItems(newOrderNums);
            //orderNum.getItems().remove(order.getOrderNum()-1);
            orderList.remove(order);*/
