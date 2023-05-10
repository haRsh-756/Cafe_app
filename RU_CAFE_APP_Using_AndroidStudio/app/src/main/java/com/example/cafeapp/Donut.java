package com.example.cafeapp;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * donut classs
 * @author harshpatel, giancarlo andretta
 */
public class Donut extends MenuItem implements Parcelable {

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

    protected Donut(Parcel in) {
        itemName = in.readString();
        qty = in.readInt();
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
        //final double salesTax = 6.625;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(itemName);
        parcel.writeInt(qty);
    }
    public static final Creator<Donut> CREATOR = new Creator<Donut>() {
        @Override
        public Donut createFromParcel(Parcel in) {
            return new Donut(in);
        }

        @Override
        public Donut[] newArray(int size) {
            return new Donut[size];
        }
    };
}
