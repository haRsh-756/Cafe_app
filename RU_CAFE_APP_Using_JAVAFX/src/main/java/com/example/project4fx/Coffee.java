package com.example.project4fx;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Coffee class is a subclass of MenuItem and represents a coffee order with its cup size, add-ins, and quantity.
 * Each add-in costs $0.30. There are different cup sizes for the coffee: Short, Tall, Grande and Venti. The base
 * price for a Short black coffee is $1.89. The price increases $0.40 for the next cup size. For example, a Tall black
 * coffee would be $2.29, and a Grande black coffee would be $2.69. A Short coffee with 2 add-ins would be $2.49.
 * @author harsh_patel, giancarlo_andretta
 */
public class Coffee extends MenuItem implements Serializable {
    /**
     * The size of the coffee cup
     */
    private int CUP_SIZE;
    /**
     * The list of add-ins in the coffee
     */
    private ArrayList<String> addIns;
    /**
     * The quantity of coffee
     */
    private int qty;
    /**
     * Initializes a default coffee order with zero size, no add-ins, and zero quantity.
     */
    private static final int Short = 0;
    private static final int Tall  = 1;
    private static final int Grande = 2;
    private static final int Venti = 3;
    private static final double basePrice = 1.89;
    private static final double nextCupPrice = 0.40;

    public Coffee(){
        this.CUP_SIZE = 0;
        this.addIns = new ArrayList<>();
        this.qty = 0;
    }
    /**
     * Initializes a coffee order with the given cup size, add-ins, and quantity.
     * @param CUP_SIZE the size of the coffee cup
     * @param addIns the list of add-ins in the coffee
     * @param quantity the quantity of coffee
     */
    public Coffee(int CUP_SIZE, ArrayList<String> addIns, int  quantity){
        this.CUP_SIZE = CUP_SIZE;
        this.addIns = addIns;
        this.qty = quantity;
    }
    /**
     * Sets the size of the coffee cup.
     * @param cupSize the size of the coffee cup
     */
    public void setCupSize(int cupSize){
        this.CUP_SIZE = cupSize;
    }
    /**
     * Sets the quantity of coffee.
     * @param qty the quantity of coffee
     */
    public void setQuantity(int qty){
        this.qty = qty;
    }
    /**
     * Returns the quantity of coffee.
     * @return the quantity of coffee
     */
    public int getQty() {
        return qty;
    }
    /**
     * Sets the size of the coffee cup and add-ins.
     * @param cupSize the size of the coffee cup
     * @param addIns the list of add-ins in the coffee
     */
    public void setCupSizeAndAddIns(int cupSize, ArrayList<String> addIns){
        this.CUP_SIZE = cupSize;
        this.addIns = addIns;
    }
    /**
     *Each add-in costs $0.30. There are different cup sizes for the coffee: Short, Tall, Grande
     * and Venti. The base price for a Short black coffee is $1.89. The price increases $0.40 for the next cup size. For
     * example, a Tall black coffee would be $2.29, and a Grande black coffee would be $2.69. A Short coffee with 2 add-
     * ins would be $2.49.
     * @return cup price
     */
    private double getCupPrice(){
        if(CUP_SIZE == Short){
            return basePrice;
        }
        else if(CUP_SIZE == Tall){
            return basePrice + nextCupPrice;
        }
        else if(CUP_SIZE == Grande){
            return basePrice + (nextCupPrice * Grande);
        }
        else{
            return basePrice + (nextCupPrice * Venti);
        }
    }
    /**
     * Calculates and returns the price of the coffee order, including the cost of add-ins.
     * @return the price of the coffee order, including the cost of add-ins
     */
    @Override
    public double itemPrice() {
        final double addInPrice = 0.30;
        final String hasBlack = "Black";
        if(addIns.contains(hasBlack)){
            return (((addIns.size()-1) * addInPrice) + getCupPrice()) * qty;
        }
        else {
            return ((addIns.size() * addInPrice) + getCupPrice()) * qty;
        }
    }

    /**
     * compares coffee object instance
     * @param obj coffee object
     * @return comparison value
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Coffee coffee){
            return this.CUP_SIZE == coffee.CUP_SIZE
                    && this.addIns.equals(coffee.addIns);
        }
        return false;
    }

    /**
     * string representation of Coffee with size, addIns, and quantity
     * @return string representation
     */
    @Override
    public String toString() {
        if(CUP_SIZE == Short){
            return "Size: " + "Short " + addIns + " [" + qty + "]";
        }
        else if(CUP_SIZE == Tall){
            return "Size: " + "Tall " + addIns + " [" + qty + "]";
        }
        else if(CUP_SIZE == Grande){
            return "Size: " + "Grande " + addIns + " [" + qty + "]";
        }
        else {
            return "Size: " + "Venti " + addIns + " [" + qty + "]";
        }
    }
}
