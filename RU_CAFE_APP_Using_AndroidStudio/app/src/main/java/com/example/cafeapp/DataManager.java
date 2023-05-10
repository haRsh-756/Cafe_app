package com.example.cafeapp;

import java.util.ArrayList;
import java.util.List;

/**
 * singleton class that manages the data
 * data will be of menuItem and Order objects
 * @author harsh_patel, giancarlo_andretta
 */
public final class DataManager {
    /**
     * class instance
     */
    private static DataManager instance = null;
    /**
     * itemList of MenuItem objects instance
     */
    private List<MenuItem> itemsList = new ArrayList<>();
    /**
     * orderList of Order objects instance
     */
    private List<Order> orderList = new ArrayList<>();
    /**
     * orderNum instance
     */
    private static int orderNum = 1;

    /**
     * default constructor
     */
    private DataManager() {
        // private constructor to prevent instantiation from outside
    }

    /**
     * static class method to getInstance of final object
     * @return instance of DataManger
     */
    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    /**
     * generates unique order number
     * @return order number
     */
    public int generateOrderNum(){
        return orderNum++;
    }

    /**
     * set the itemList
     * @param itemsList list of menuItem
     */
    public void setItemsList(List<MenuItem> itemsList) {
        this.itemsList = itemsList;
    }

    /**
     * get the itemList
     * @return itemList
     */
    public List<MenuItem> getItemsList() {
        return this.itemsList;
    }

    /**
     * get the orderlist
     * @return orderList
     */
    public List<Order> getOrderList() {
        return orderList;
    }

    /**
     * set the orderList
     * @param orderList orderList
     */
    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }
}
