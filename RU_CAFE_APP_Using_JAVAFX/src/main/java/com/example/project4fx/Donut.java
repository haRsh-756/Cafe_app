package com.example.project4fx;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * donut class
 * @author harshpatel, giancarlo andretta
 */
public class Donut extends MenuItem implements Serializable {

    /**
     * donut type
     */
    private DonutType donutType;
    /**
     * itemsName
     */
    private String itemName;
    /**
     * qty
     */
    private int qty;

    /**
     * DOnut
     */

    public Donut(){
        this.donutType = null;
        this.qty = 0;
        this.itemName = "";
    }

    /**
     * Donut
     * @param donutType donuttype
     * @param itemName itemname
     * @param qty qty
     */
    public Donut(DonutType donutType, String itemName, int qty){
        this.donutType = donutType;
        this.itemName = itemName;
        this.qty = qty;
    }

    /**
     * donut type
     * @param donutType donut type
     */
    public void setDonutType(DonutType donutType){
        this.donutType = donutType;
    }

    /**
     * item name
     * @return getItem Name
     */

    public String getItemName() {
        return itemName;
    }

    /**
     * item name
     * @param itemName item name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * qty
     * @return get qty
     */
    public int getQty() {
        return qty;
    }

    /**
     * set qty
     * @param qty qty
     */
    public void setQty(int qty) {
        this.qty = qty;
    }

    /**
     * donut type
     * @return donut type
     */
    public DonutType getDonutType(){
        return this.donutType;
    }

    /**
     * item and list
     * @param selectedItem item
     * @param yeastDonutList list
     * @param cakeDonutList list
     * @param donut_holes_List list
     * @return
     */
    public DonutType find(String selectedItem, ArrayList<String> yeastDonutList,
                          ArrayList<String> cakeDonutList, ArrayList<String> donut_holes_List){
        for(String i: yeastDonutList){
            if(i.equalsIgnoreCase(selectedItem)){
                return DonutType.YEAST;
            }
        }
        for(String i: cakeDonutList){
            if(i.equalsIgnoreCase(selectedItem)){
                return DonutType.CAKE;
            }
        }
        for(String i: donut_holes_List){
            if(i.equalsIgnoreCase(selectedItem)){
                return DonutType.DONUT_HOLE;
            }
        }
        return null;
    }

    /**
     * item price
     * @return items price
     */
    @Override
    public double itemPrice() {
        if(this.donutType != null) {
            return this.donutType.price * qty;
        }
        return -1;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Donut donut){
            return this.donutType == donut.donutType
                     && this.itemName.equalsIgnoreCase(donut.itemName);
        }
        return false;
    }

    /**
     * to string method
     */
    @Override
    public String toString() {
        return this.donutType.name() + ": " + this.itemName + " [" + this.qty + "]";
    }
}
/*
//yeastDonutItems = new ArrayList<>(List.of("jelly", "glazed", "strawberry frosted", "vanilla frosted", "sugar", "crispy creme"));
        //cakeDonutItems = new ArrayList<>(List.of("old fashioned", "glazed blueberry", "cinnamon sugar", "apple cider", "chocolate glazed"));
        //donut_holes = new ArrayList<>(List.of("glazed holes", "glazed chocolate holes", "jelly holes", "pumpkin holes"));
 */
 /*public Donut(ArrayList<String> options1, ArrayList<String> options2, ArrayList<String> options3){
        this.yeastDonutList = options1;
        this.cakeDonutList = options2;
        this.donut_holes_List = options3;
    }*/