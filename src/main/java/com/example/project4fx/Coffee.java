package com.example.project4fx;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * coffee class
 * @author harshpatel, giancarlo andretta
 */
public class Coffee extends MenuItem implements Serializable {
    /**
     * cup size
     */
    private int CUP_SIZE;
    /**
     * add ins
     */
    private ArrayList<String> addIns;
    /**
     * qty
     */
    private int qty;

    /**
     * coffee
     */

    public Coffee(){
        this.CUP_SIZE = 0;
        this.addIns = new ArrayList<>();
        this.qty = 0;
    }

    /**
     * coffee
     * @param CUP_SIZE cup size
     * @param addIns add ins
     * @param quantity qty
     */
    public Coffee(int CUP_SIZE, ArrayList<String> addIns, int  quantity){
        this.CUP_SIZE = CUP_SIZE;
        this.addIns = addIns;
        this.qty = quantity;
    }
    /**
     * cup size
     */
    public void setCupSize(int cupSize){
        this.CUP_SIZE = cupSize;
    }

    /**
     * qty
     * @param qty qty
     */
    public void setQuantity(int qty){
        this.qty = qty;
    }

    public int getQty() {
        return qty;
    }

    /**
     * set cup and add ins
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
        final int Short = 0;
        final int Tall  = 1;
        final int Grande = 2;
        final int Venti = 3;
        final double basePrice = 1.89;
        final double nextCupPrice = 0.40;
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
     * item price
     * @return item price
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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Coffee coffee){
            return this.CUP_SIZE == coffee.CUP_SIZE
                    && this.addIns.equals(coffee.addIns);
        }
        return false;
    }

    /**
     * to string
     * @return to string
     */
    @Override
    public String toString() {
        final int Short = 0;
        final int Tall  = 1;
        final int Grande = 2;
        //final int Venti = 3;
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
