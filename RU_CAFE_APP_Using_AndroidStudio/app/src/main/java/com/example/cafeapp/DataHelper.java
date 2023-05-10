package com.example.cafeapp;

public class DataHelper {
    /**
     * name instance
     */
    private String name;
    /**
     * donutType instance
     */
    private String donutType;
    /**
     * qunatity instance
     */
    private int quantity;
    /**
     * price instance
     */
    private double price;
    /**
     * datahelper object with name, donutType, quantity, and price
     * @param name name of the donut
     * @param donutType type of the donut
     * @param quantity quantity of the donut
     * @param price price of the donut
     */
    public DataHelper(String name, String donutType, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.donutType = donutType;
        this.price = price;
    }

    /**
     * returns price of the donut
     * @return price of the donut
     */
    public double getPrice() {
        return price;
    }

    /**
     * sets the price of donut
     * @param price price of donut
     */

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * returns donuttype
     * @return donuttype
     */
    public String getDonutType() {
        return donutType;
    }
    /**
     * sets the donutType
     */

    public void setDonutType(String donutType) {
        this.donutType = donutType;
    }
    /**
     * returns name of the donut
     */
    public String getName() {
        return name;
    }

    /**
     * returns quantity of the donut
     * @return quantity of the donut
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * sets the quantity of the donut
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
